package org.skeleton.generator.repository.dao.datasource.interfaces;

import java.util.List;

import org.skeleton.generator.exception.ReadBackupFailureException;

/**
 * This class provides a representation of Backup data through a List of object arrays<br/>
 * Depending on the implementation, the data will be fetched from a database query, a csv file, ...
 * @author Nicolas Thibault
 *
 */
public interface BackupReader {

	List<Object[]> readBackupArgs() throws ReadBackupFailureException;
}
