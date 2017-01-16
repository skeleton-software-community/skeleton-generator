package org.sklsft.generator.repository.backup.command.impl;

import javax.inject.Inject;

import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.command.interfaces.BackupArgumentsCommand;
import org.sklsft.generator.repository.backup.command.interfaces.BackupCommand;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.reader.impl.BackupArgumentReaderFactory;
import org.sklsft.generator.repository.backup.reader.interfaces.BackupArgumentReader;
import org.sklsft.generator.util.naming.JavaClassNaming;
import org.springframework.stereotype.Component;

/**
 * return a BackupCommand depending on which {@link PersistenceMode} will be discovered<br>
 * <li>CMD : uses a {@link BackupCommandRawImpl}
 * <li>else : uses a {@link BackupCommandWithArgumentsImpl}
 * 
 * in the last case, we need to build it by injecting a {@link BackupArgumentReader} and a {@link BackupArgumentsCommand}<br>
 * this is built with the corresponding factories : {@link BackupArgumentReaderFactory} and {@link BackupArgumentsCommandFactory}
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class BackupCommandFactory {
	
	@Inject
	private BackupArgumentReaderFactory readerFactory;
	
	@Inject
	private BackupArgumentsCommandFactory backupArgumentsCommandFactory;
	
	@Inject
	private BackupCommandRawImpl backupCommandRawImpl;


	
	public BackupCommand getBackupCommand(Table table, PersistenceMode mode, InputDataSourceProvider inputDataSourceProvider) {
		
		switch (mode) {
			case CMD:
				return backupCommandRawImpl;
			default:
				return getBackupArgumentCommand(table, mode, inputDataSourceProvider);
		}
	}

	private BackupCommand getBackupArgumentCommand(Table table, PersistenceMode mode, InputDataSourceProvider inputDataSourceProvider) {
		
		BackupArgumentReader argumentReader = readerFactory.getBackupArgumentReader(mode, inputDataSourceProvider);
		
		BackupArgumentsCommand command = backupArgumentsCommandFactory.getCommand(JavaClassNaming.getObjectName(table.originalName) + "Command");
		
		return new BackupCommandWithArgumentsImpl(argumentReader, command);
	}
}
