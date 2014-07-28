package org.sklsft.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.command.impl.JdbcCommandAbstractFactory;
import org.sklsft.generator.bc.backup.command.interfaces.JdbcCommandFactory;
import org.sklsft.generator.exception.PopulateTableFailureException;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.datasource.impl.BackupCommandArguments;
import org.sklsft.generator.repository.backup.jdbc.interfaces.JdbcCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TablePopulator{
	
	@Autowired
	private JdbcCommandAbstractFactory commandAbstractFactory;
	
	
	public void populateTable(Table table, DataSource dataSource, BackupCommandArguments commandArgs) throws PopulateTableFailureException {
					
		List<Object[]> argsList = commandArgs.getArguments();
		
		JdbcCommandFactory commandFactory = commandAbstractFactory.getFactory(commandArgs.getType());
			
		JdbcCommand command = commandFactory.getCommand(dataSource, table, argsList);
		command.execute();

	}
}
