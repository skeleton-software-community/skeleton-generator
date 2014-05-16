package org.skeleton.generator.bc.factory.command.jdbc.impl;

import org.skeleton.generator.bc.factory.command.jdbc.interfaces.JdbcCommandFactory;
import org.skeleton.generator.exception.UnknownJdbcCommandType;
import org.skeleton.generator.model.backup.PopulateCommandType;
import org.springframework.stereotype.Component;

@Component
public class JdbcCommandAbstractFactory {

	public JdbcCommandFactory getFactory(PopulateCommandType type) {
		switch (type) {
			case INSERT:
				return new JdbcInsertCommandFactory();
			case UPDATE:
				return new JdbcUpdateCommandFactory();
			default:
				throw new UnknownJdbcCommandType(type);
		}
	}

}
