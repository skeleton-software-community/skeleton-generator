package org.skeleton.generator.bc.factory.reader;

import org.skeleton.generator.exception.UnhandledPersistenceModeException;
import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.TextDelimitedFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.impl.XmlFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupArgumentReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.DataSourceProvider;
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
