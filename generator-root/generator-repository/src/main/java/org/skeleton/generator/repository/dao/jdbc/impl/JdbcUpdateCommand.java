package org.skeleton.generator.repository.dao.jdbc.impl;

import javax.sql.DataSource;

import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;
import org.skeleton.generator.repository.jdbc.SQLNaming;
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
public class JdbcUpdateCommand implements JdbcCommand {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcUpdateCommand.class);

	/*
	 * properties
	 */
	private SimpleJdbcCall jdbcCall;
	private Table table;
	private Object[] args;
	
	/*
	 * constructor
	 */
	public JdbcUpdateCommand(DataSource dataSource, Table table, Object[] args) {
		this.jdbcCall = new SimpleJdbcCall(dataSource);
		this.jdbcCall.setProcedureName(SQLNaming.getUpdateProcedureName(table.name, table.myPackage.model.project.databaseEngine));
		this.table = table;
		this.args = args;
	}
	
	public void execute() {
		String message = "execute update for table : " + table.name + " - args : ";
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
