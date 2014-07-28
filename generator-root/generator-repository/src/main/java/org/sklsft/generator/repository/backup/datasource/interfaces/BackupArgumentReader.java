package org.sklsft.generator.repository.backup.datasource.interfaces;

import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.repository.backup.datasource.impl.BackupCommandArguments;

/**
 * This class provides a representation of Backup data through a List of object arrays<br/>
 * Depending on the implementation, the data will be fetched from a database query, a csv file, ...
 * @author Nicolas Thibault
 *
 */
public interface BackupArgumentReader {

	BackupCommandArguments readBackupArgs(String backupFilePath) throws ReadBackupFailureException;
}
