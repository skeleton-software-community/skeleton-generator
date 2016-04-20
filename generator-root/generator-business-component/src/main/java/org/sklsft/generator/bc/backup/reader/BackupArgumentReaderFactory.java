package org.sklsft.generator.bc.backup.reader;

import org.sklsft.generator.exception.UnhandledPersistenceModeException;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.datasource.impl.PlainTextFileReader;
import org.sklsft.generator.repository.backup.datasource.impl.TextDelimitedFileBackupReader;
import org.sklsft.generator.repository.backup.datasource.impl.XmlFileBackupReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.springframework.stereotype.Component;

@Component
public class BackupArgumentReaderFactory {

	public BackupArgumentReader getBackupArgumentReader(PersistenceMode type, InputDataSourceProvider inputSourceProvider) {
		switch (type) {
		case CSV:
			return new TextDelimitedFileBackupReader();
		case XML:
			return new XmlFileBackupReader(inputSourceProvider);
		case CMD:
			return new PlainTextFileReader();
		default:
			throw new UnhandledPersistenceModeException("Unhandled persistenceMode " + type + " for reading backup arguments");
		}
	}
}
