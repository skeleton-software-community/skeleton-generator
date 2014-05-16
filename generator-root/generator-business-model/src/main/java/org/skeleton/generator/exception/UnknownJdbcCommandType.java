package org.skeleton.generator.exception;

import org.skeleton.generator.model.backup.PopulateCommandType;

public class UnknownJdbcCommandType extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2208508992365704664L;

	public UnknownJdbcCommandType(PopulateCommandType type) {
		super("Unknown command implementation " + type);
	}
	
}
