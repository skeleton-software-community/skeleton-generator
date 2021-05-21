package org.sklsft.generator.persistence.backup.command.impl;

import org.sklsft.generator.persistence.backup.command.interfaces.BackupArgumentsCommand;
import org.sklsft.generator.persistence.backup.command.interfaces.BackupCommand;
import org.sklsft.generator.persistence.backup.reader.interfaces.BackupArgumentReader;
import org.sklsft.generator.persistence.backup.reader.model.BackupArguments;

/**
 * This implementation of a {@link BackupCommand} has 
 * <li>a {@link BackupArgumentReader}
 * <li>a {@link BackupArgumentsCommand}
 * 
 * It works this way :<br>
 * the reader fetches the {@link BackupArguments}<br>
 * the {@link BackupArgumentsCommand} executes with the fetched arguments
 * 
 * @author Nicolas Thibault
 *
 */
public class BackupCommandWithArgumentsImpl implements BackupCommand {
	
	private BackupArgumentReader argumentReader;
	private BackupArgumentsCommand command;
	
		
	public BackupCommandWithArgumentsImpl(BackupArgumentReader argumentReader,
			BackupArgumentsCommand command) {
		super();
		this.argumentReader = argumentReader;
		this.command = command;
	}



	@Override
	public void execute(String path) {
		
		BackupArguments commandArgs = argumentReader.readBackupArgs(path);
		
		command.execute(commandArgs);
	}
}
