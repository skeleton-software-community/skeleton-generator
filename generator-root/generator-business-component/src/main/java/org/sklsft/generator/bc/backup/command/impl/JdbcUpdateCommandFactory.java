package org.sklsft.generator.bc.backup.command.impl;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.command.interfaces.JdbcCommandFactory;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.repository.backup.command.impl.JdbcUpdateCommand;
import org.sklsft.generator.repository.backup.command.interfaces.Command;

public class JdbcUpdateCommandFactory implements JdbcCommandFactory{

	@Override
	public Command getCommand(DataSource dataSource, Table table) {
		return new JdbcUpdateCommand(dataSource, table);
	}

}
