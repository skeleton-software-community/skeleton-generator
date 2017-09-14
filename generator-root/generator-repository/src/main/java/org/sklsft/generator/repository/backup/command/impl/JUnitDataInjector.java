package org.sklsft.generator.repository.backup.command.impl;

import javax.inject.Inject;

import org.sklsft.generator.repository.backup.command.interfaces.BackupArgumentsCommand;
import org.sklsft.generator.repository.backup.reader.impl.TextDelimitedFileBackupReader;
import org.sklsft.generator.repository.backup.reader.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.reader.model.BackupArguments;
import org.springframework.stereotype.Component;

@Component
public class JUnitDataInjector {
	
	@Inject
	private BackupArgumentsCommandFactory commandFactory;
		
	
	public void inject(String filePath, Class<?> commandClass) {
		BackupArgumentReader argumentReader = new TextDelimitedFileBackupReader();
		BackupArguments commandArgs = argumentReader.readBackupArgs(filePath);
		
		BackupArgumentsCommand command = commandFactory.getCommand(commandClass);
		command.execute(commandArgs);
	}
}
