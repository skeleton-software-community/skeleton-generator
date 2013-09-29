package org.skeleton.generator.bc.command.jdbc.impl;

import org.skeleton.generator.model.om.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class JdbcInsertCommand {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcInsertCommand.class);

	/*
	 * properties
	 */
	private SimpleJdbcCall jdbcCall;
	private Table table;
	private Object[] args;
	
	/*
	 * constructor
	 */
	public JdbcInsertCommand(SimpleJdbcCall jdbcCall, Table table, Object[] args) {
		this.jdbcCall = jdbcCall;
		this.table = table;
		this.args = args;
	}
	
	public void execute() {
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
