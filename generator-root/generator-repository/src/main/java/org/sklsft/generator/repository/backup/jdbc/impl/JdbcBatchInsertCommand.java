package org.sklsft.generator.repository.backup.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
	private TransactionTemplate transactionTemplate;
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
		this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
	}
	
	public void execute() {
		final BatchDataJdbcCommand batchData = new BatchDataJdbcCommand(argsList,BATCH_SIZE);
		//while all the data  have not been processed
		while (batchData.hasNext()) {
			int batchStartPosition = batchData.getPosition();
			try {
				 transactionTemplate.execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						// process the batch insert 
						//  (max  BATCH_SIZE rows are processed in one execution) 
						jdbcTemp.batchUpdate(query, batchData);
						return null;
					}
				});
				
			} catch (Exception e) {
				if (batchData.getBatchSize() > 1) {
					// to insert a maximum of data, we retry with a step of one 
					logger.error("exception found during batch process, restart at data position " + batchStartPosition + " with a step equal to 1");
					batchData.forceRestartPosition(batchStartPosition);
					batchData.setBatchSize(1);
				} else {
					// the insert is logged, and the insert continue to next value
					logger.error("batch insert for table " + table.name +  "failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
				}
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
		private List<Object[]> 		argsList;
		private int					batchSize;
		private int					position;
		public BatchDataJdbcCommand(List<Object[]> argsList, int defaultBatchSize) {
			this.argsList = argsList;
			this.batchSize = defaultBatchSize;
			this.position = 0;
		}
		
		public boolean hasNext() {
			return position < argsList.size();
		}
		
		@Override
		public int getBatchSize() {						
			return batchSize;
		}
		
		public void forceRestartPosition(int position) {
			this.position = position;
		}
		
		public void setBatchSize(int batchSize) {
			this.batchSize = batchSize;
		}
		
		public int getPosition() {
			return position;
		}
		
		@Override
		protected boolean setValuesIfAvailable(PreparedStatement ps, int i)
				throws SQLException {
			if (hasNext()) {
				Object[] args = argsList.get(position);
				position++;
				
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
