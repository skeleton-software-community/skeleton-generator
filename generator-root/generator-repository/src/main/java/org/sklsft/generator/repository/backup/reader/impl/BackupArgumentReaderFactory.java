package org.sklsft.generator.repository.backup.reader.impl;

import org.sklsft.generator.exception.UnhandledPersistenceModeException;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.reader.interfaces.BackupArgumentReader;
import org.springframework.stereotype.Component;

/**
 * get a {@link BackupArgumentReader} dependeing on the {@link PersistenceMode}
 * <li>CSV : {@link TextDelimitedFileBackupReader}
 * <li>XML : {@link XmlFileBackupReader}
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class BackupArgumentReaderFactory {

	public BackupArgumentReader getBackupArgumentReader(PersistenceMode type, InputDataSourceProvider inputSourceProvider) {
		switch (type) {
		case CSV:
			return new TextDelimitedFileBackupReader();
		case XML:
			return new XmlFileBackupReader(inputSourceProvider);
		default:
			throw new UnhandledPersistenceModeException("Unhandled persistenceMode " + type + " for reading backup arguments");
		}
	}
}
