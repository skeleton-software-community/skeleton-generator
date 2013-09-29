package org.skeleton.generator.bc.command.jdbc.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleScriptCommand {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(SimpleScriptCommand.class);
	
	/*
	 * properties
	 */
	private DataSource dataSource;
	private String script;
	
	/*
	 * constructor
	 */
	public SimpleScriptCommand(DataSource dataSource, String script) {
		this.dataSource = dataSource;
		this.script = script;
	}
	
	public void execute() throws SQLException {
		
		try (Connection connection = dataSource.getConnection()) {
			
			connection.setAutoCommit(true);
			
			String[] tokens = script.split("/");
			
			for (String token:tokens) {
			
				Statement statement = connection.createStatement();
				
				try {
					logger.info("executing statement : " + token);
					statement.execute(token);
				} catch (SQLException e) {
					logger.error("statement failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
				}
			}
		}
	}
}
