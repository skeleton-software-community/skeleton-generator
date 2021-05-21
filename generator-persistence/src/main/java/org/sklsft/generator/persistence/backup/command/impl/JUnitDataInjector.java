package org.sklsft.generator.persistence.backup.command.impl;

import javax.inject.Inject;

import org.sklsft.generator.persistence.backup.command.interfaces.BackupArgumentsCommand;
import org.sklsft.generator.persistence.backup.reader.impl.StandardCsvFileBackupReader;
import org.sklsft.generator.persistence.backup.reader.interfaces.BackupArgumentReader;
import org.sklsft.generator.persistence.backup.reader.model.BackupArguments;
import org.springframework.stereotype.Component;

@Component
public class JUnitDataInjector {
	
	@Inject
	private BackupArgumentsCommandFactory commandFactory;
		
	
	public void inject(String filePath, Class<?> commandClass) {
		BackupArgumentReader argumentReader = new StandardCsvFileBackupReader();
		BackupArguments commandArgs = argumentReader.readBackupArgs(filePath);
		
		BackupArgumentsCommand command = commandFactory.getCommand(commandClass);
		command.execute(commandArgs);
	}
}
