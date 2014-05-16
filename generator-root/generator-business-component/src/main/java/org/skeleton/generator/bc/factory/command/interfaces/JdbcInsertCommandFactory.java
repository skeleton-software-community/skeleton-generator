package org.skeleton.generator.bc.factory.command.interfaces;

import javax.sql.DataSource;

import org.skeleton.generator.bc.factory.command.JdbcCommandFactory;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.jdbc.impl.JdbcInsertCommand;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;

public class JdbcInsertCommandFactory implements JdbcCommandFactory{

	@Override
	public JdbcCommand getCommand(DataSource dataSource, Table table,
			Object[] args) {
		return new JdbcInsertCommand(dataSource, table, args);
	}

}
