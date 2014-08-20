package org.sklsft.generator.repository.backup.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.jdbc.interfaces.JdbcCommand;
import org.sklsft.generator.repository.util.SQLNaming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractInterruptibleBatchPreparedStatementSetter;

/**
 * Using the command pattern, when populating a database<br/>
 * This command is associated to a table and uses a spring {@link JdbcTemplate} with arguments as an array of objects 
 * Used to execute an insert statement using a "find by code" stored function
 * and finally executes a stored procedure to update audit table for inserted lines
 * @author Michael Fernandez
 *
 */
public class JdbcBatchInsertCommand implements JdbcCommand {
	
	public static final int BATCH_SIZE = 5000;
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcBatchInsertCommand.class);
	

	/*
	 * properties
	 */
	private JdbcTemplate jdbcTemp;
	private SimpleJdbcCall jdbcCallPostIns;
	private Table table;
	private List<Object[]> argsList;
	private String	query;
	
	/*
	 * constructor
	 */
	public JdbcBatchInsertCommand(DataSource dataSource, Table table, List<Object[]> argsList, String sqlQuery) {
		this.jdbcTemp = new JdbcTemplate(dataSource);
		String postInsertProcedure = SQLNaming.getPostInsertProcedureName(table.name, table.myPackage.model.project.databaseEngine);
		if (postInsertProcedure != null) {
			this.jdbcCallPostIns = new SimpleJdbcCall(dataSource);
			this.jdbcCallPostIns.setProcedureName(postInsertProcedure);
		}
		this.table = table;
		this.argsList = argsList;
		this.query = sqlQuery;
	}
	
	public void execute() {
		BatchDataJdbcCommand batchData = new BatchDataJdbcCommand(argsList);
		//while all the data  have not been processed
		while (batchData.hasNext()) {
			try {
				// process the batch insert 
				//  (max  BATCH_SIZE rows are processed in one execution) 
				jdbcTemp.batchUpdate(query, batchData);
			} catch (Exception e) {
				logger.error("batch insert for table " + table.name +  "failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
			}
		}
		if (jdbcCallPostIns != null) {
			logger.info("start postinsert for table : " + table.name);
			try {
				jdbcCallPostIns.execute();
			} catch (Exception e) {
				logger.error("postinsert execution for table " + table.name +  "failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());				
			}
			logger.info("end postinsert for table : " + table.name);
		}
	}

	/*
	 * Class used to process data by batch using the limit of BATCH_SIZE.
	 */
	private class BatchDataJdbcCommand extends AbstractInterruptibleBatchPreparedStatementSetter {
		private Iterator<Object[]> argsList;
		public BatchDataJdbcCommand(List<Object[]> argsList) {
			this.argsList = argsList.iterator();
		}
		
		public boolean hasNext() {
			return argsList.hasNext();
		}
		
		@Override
		public int getBatchSize() {						
			return BATCH_SIZE;
		}
		@Override
		protected boolean setValuesIfAvailable(PreparedStatement ps, int i)
				throws SQLException {
			if (argsList.hasNext()) {
				Object[] args = argsList.next();
				
				String message = "prepare insert for table : " + table.name + " - args : ";
				for (Object arg:args) {
					message += "[" + arg + "]";
				}
				logger.info(message);
				
				for (int arg = 0; arg < args.length;++arg) {
					ps.setObject(arg+1, args[arg]);
				}
				
				return true;
			} else {
				return false;
			}

		}
	}
}
