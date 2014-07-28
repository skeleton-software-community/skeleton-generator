package org.sklsft.generator.bc.backup.command.impl;

import org.sklsft.generator.bc.backup.command.interfaces.JdbcCommandFactory;
import org.sklsft.generator.exception.UnknownJdbcCommandType;
import org.sklsft.generator.model.backup.PopulateCommandType;
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
