package org.sklsft.generator.bc.factory.command.jdbc.impl;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.factory.command.jdbc.interfaces.JdbcCommandFactory;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.dao.jdbc.impl.JdbcUpdateCommand;
import org.sklsft.generator.repository.dao.jdbc.interfaces.JdbcCommand;

public class JdbcUpdateCommandFactory implements JdbcCommandFactory{

	@Override
	public JdbcCommand getCommand(DataSource dataSource, Table table,
			List<Object[]> argsList) {
		return new JdbcUpdateCommand(dataSource, table, argsList);
	}

}
