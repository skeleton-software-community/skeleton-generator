package org.sklsft.generator.bc.backup.command.impl;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.command.interfaces.JdbcCommandFactory;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.command.impl.JdbcInsertCommand;
import org.sklsft.generator.repository.backup.command.interfaces.Command;

public class JdbcInsertCommandFactory implements JdbcCommandFactory{

	@Override
	public Command getCommand(DataSource dataSource, Table table) {
		return new JdbcInsertCommand(dataSource, table);
	}

}
