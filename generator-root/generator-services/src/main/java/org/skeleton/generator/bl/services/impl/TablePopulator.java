package org.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bc.factory.command.JdbcCommandAbstractFactory;
import org.skeleton.generator.bc.factory.command.JdbcCommandFactory;
import org.skeleton.generator.exception.PopulateTableFailureException;
import org.skeleton.generator.model.backup.BackupCommandArguments;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;


public class TablePopulator{
	
	/*
	 * properties
	 */
	private JdbcCommandAbstractFactory commandAbstractFactory;
	
	/*
	 * constructor
	 */
	public TablePopulator() {
		this.commandAbstractFactory = new JdbcCommandAbstractFactory();
	}
	

	public void populateTable(Table table, DataSource dataSource, BackupCommandArguments commandArgs) throws PopulateTableFailureException {
					
		List<Object[]> argsList = commandArgs.getArguments();
		
		JdbcCommandFactory commandFactory = commandAbstractFactory.getFactory(commandArgs.getType());
			
		for (Object[] args:argsList) {
			JdbcCommand command = commandFactory.getCommand(dataSource, table, args);
			command.execute();
		}
	}
	

}
