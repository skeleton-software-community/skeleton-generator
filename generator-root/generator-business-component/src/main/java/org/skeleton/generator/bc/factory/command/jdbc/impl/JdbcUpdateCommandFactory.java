package org.skeleton.generator.bc.factory.command.jdbc.impl;

import javax.sql.DataSource;

import org.skeleton.generator.bc.factory.command.jdbc.interfaces.JdbcCommandFactory;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.jdbc.impl.JdbcUpdateCommand;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;

public class JdbcUpdateCommandFactory implements JdbcCommandFactory{

	@Override
	public JdbcCommand getCommand(DataSource dataSource, Table table,
			Object[] args) {
		return new JdbcUpdateCommand(dataSource, table, args);
	}

}
