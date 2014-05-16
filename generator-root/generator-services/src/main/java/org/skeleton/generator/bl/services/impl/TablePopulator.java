package org.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bc.factory.command.jdbc.impl.JdbcCommandAbstractFactory;
import org.skeleton.generator.bc.factory.command.jdbc.interfaces.JdbcCommandFactory;
import org.skeleton.generator.exception.PopulateTableFailureException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.BackupCommandArguments;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TablePopulator{
	
	@Autowired
	private JdbcCommandAbstractFactory commandAbstractFactory;
	
	
	public void populateTable(Table table, DataSource dataSource, BackupCommandArguments commandArgs) throws PopulateTableFailureException {
					
		List<Object[]> argsList = commandArgs.getArguments();
		
		JdbcCommandFactory commandFactory = commandAbstractFactory.getFactory(commandArgs.getType());
			
		for (Object[] args:argsList) {
			JdbcCommand command = commandFactory.getCommand(dataSource, table, args);
			command.execute();
		}
	}
}
