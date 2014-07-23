package org.sklsft.generator.bc.factory.reader;

import org.sklsft.generator.exception.UnhandledPersistenceModeException;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.dao.datasource.impl.TextDelimitedFileBackupReader;
import org.sklsft.generator.repository.dao.datasource.impl.XmlFileBackupReader;
import org.sklsft.generator.repository.dao.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.dao.datasource.interfaces.DataSourceProvider;
import org.springframework.stereotype.Component;

@Component
public class BackupArgumentReaderFactory {

	public BackupArgumentReader getBackupArgumentReader(PersistenceMode type, DataSourceProvider inputSourceProvider, Table table) {
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
