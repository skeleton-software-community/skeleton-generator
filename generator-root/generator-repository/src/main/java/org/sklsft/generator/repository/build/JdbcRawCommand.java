package org.sklsft.generator.repository.build;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to execute a multiple statement script
 * the statements must be separated by a /
 * @author Nicolas Thibault
 *
 */
public class JdbcRawCommand implements Command {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(JdbcRawCommand.class);
	
	/*
	 * properties
	 */
	private DataSource dataSource;
	private String script;
	
	/*
	 * constructor
	 */
	public JdbcRawCommand(DataSource dataSource, String script) {
		this.dataSource = dataSource;
		this.script = script;
	}
	
	public void execute() {
		
		try (Connection connection = dataSource.getConnection();				
			Statement statement = connection.createStatement();) {			
			
			connection.setAutoCommit(true);
			
			String[] tokens = script.split("/");
			
			for (String token:tokens) {				
				logger.info("executing statement : " + token);
				statement.executeUpdate(token);
			}
		} catch (SQLException e) {
			logger.error("statement failed : " + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}
}
