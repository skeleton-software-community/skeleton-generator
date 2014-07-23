package org.sklsft.generator.repository.dao.jdbc.impl;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.dao.jdbc.interfaces.JdbcCommand;
import org.sklsft.generator.repository.jdbc.SQLNaming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * Using the command pattern, when populating a database<br/>
 * This command is associated to a table and uses a spring {@link SimpleJdbcCall} with arguments as an array of objects 
 * Used to execute an insert statement using a generated "insert by code" stored procedure
 * @author Nicolas Thibault
 *
 */
public class JdbcInsertCommand implements JdbcCommand {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcInsertCommand.class);

	/*
	 * properties
	 */
	private SimpleJdbcCall jdbcCall;
	private Table table;
	private List<Object[]> argsList;
	
	/*
	 * constructor
	 */
	public JdbcInsertCommand(DataSource dataSource, Table table, List<Object[]> argsList) {
		this.jdbcCall = new SimpleJdbcCall(dataSource);
		this.jdbcCall.setProcedureName(SQLNaming.getInsertProcedureName(table.name, table.myPackage.model.project.databaseEngine));
		this.table = table;
		this.argsList = argsList;
	}
	
	public void execute() {
		for (Object[] args:argsList) {
			String message = "execute insert for table : " + table.name + " - args : ";
			for (Object arg:args) {
				message += "[" + arg + "]";
			}
			logger.info(message);
			try {
				jdbcCall.execute(args);
			} catch (Exception e) {
				logger.error(message + "failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
			}
		}
	}
}
