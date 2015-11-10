package org.sklsft.generator.bc.backup.reader;

import org.sklsft.generator.exception.UnhandledPersistenceModeException;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.datasource.impl.TextDelimitedFileBackupReader;
import org.sklsft.generator.repository.backup.datasource.impl.XmlFileBackupReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.springframework.stereotype.Component;

@Component
public class BackupArgumentReaderFactory {

	public BackupArgumentReader getBackupArgumentReader(PersistenceMode type, InputDataSourceProvider inputSourceProvider, Table table) {
		switch (type) {
			case CSV:
				return new TextDelimitedFileBackupReader(table);
			case XML:
				return new XmlFileBackupReader(inputSourceProvider, table);
			default:
				throw new UnhandledPersistenceModeException("Unhandled persistenceMode " + type + " for reading backup arguments");
		}
	}
}
