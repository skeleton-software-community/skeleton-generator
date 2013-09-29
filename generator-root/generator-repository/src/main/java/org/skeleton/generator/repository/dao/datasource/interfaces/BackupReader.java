package org.skeleton.generator.repository.dao.datasource.interfaces;

import java.util.List;

import org.skeleton.generator.exception.ReadBackupFailureException;


public interface BackupReader {

	List<Object[]> readBackupArgs() throws ReadBackupFailureException;
}
