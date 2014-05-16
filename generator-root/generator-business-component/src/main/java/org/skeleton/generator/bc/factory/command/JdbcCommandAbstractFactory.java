package org.skeleton.generator.bc.factory.command;

import org.skeleton.generator.bc.factory.command.interfaces.JdbcInsertCommandFactory;
import org.skeleton.generator.exception.UnknownJdbcCommandType;
import org.skeleton.generator.model.backup.PopulateCommandType;

public class JdbcCommandAbstractFactory {
	
	public JdbcCommandFactory getFactory(PopulateCommandType type){
		switch(type){
		case INSERT : return new JdbcInsertCommandFactory();
		case UPDATE : return new JdbcUpdateCommandFactory();
		default : throw new UnknownJdbcCommandType(type);
		}
	}
	
}
