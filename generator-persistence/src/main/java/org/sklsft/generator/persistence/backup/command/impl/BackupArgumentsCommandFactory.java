package org.sklsft.generator.persistence.backup.command.impl;

import org.sklsft.generator.persistence.backup.command.interfaces.BackupArgumentsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * searches for a generated {@link BackupArgumentsCommand} in the populator spring context
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class BackupArgumentsCommandFactory {

	@Autowired
	private ApplicationContext applicationContext;


	/**
	 * create the appropriate command depending on what table is used for
	 * population.
	 */
	public BackupArgumentsCommand getCommand(String classSimpleName) {

		return (BackupArgumentsCommand) applicationContext.getBean(classSimpleName);
	}
	
	public BackupArgumentsCommand getCommand(Class<?> commandClass) {

		return (BackupArgumentsCommand) applicationContext.getBean(commandClass);
	}
}
