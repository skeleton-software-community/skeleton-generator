package org.sklsft.generator.repository.backup.file.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.springframework.stereotype.Component;

@Component
public class BackupFileLocator {

	public PersistenceMode resolvePersistenceMode(String backupPath, int step, Table table) {

		PersistenceMode mode = resolvePersistenceModeOrNull(backupPath, step, table);

		if (mode == null) {
			throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
		} else {
			return mode;
		}
	}

	public PersistenceMode resolvePersistenceModeOrNull(String backupPath, int step, Table table) {
		if (existsFileForType(backupPath, step, table, PersistenceMode.CMD)) {
			return PersistenceMode.CMD;
		} else if (existsFileForType(backupPath, step, table, PersistenceMode.XML)) {
			return PersistenceMode.XML;
		} else if (existsFileForType(backupPath, step, table, PersistenceMode.CSV)) {
			return PersistenceMode.CSV;
		} else {
			return null;
		}
	}

	public String getBackupFilePath(String backupPath, int step, Table table, PersistenceMode mode) {
		return getPathPrefix(backupPath, step, table) + mode.getExtension();
	}

	public String getBackupFilePath(String backupPath, int step, Table table) {
		PersistenceMode mode = resolvePersistenceMode(backupPath, step, table);
		return getPathPrefix(backupPath, step, table) + mode.getExtension();
	}

	public boolean existsFileForTable(String backupPath, int maxStep, Table table) {
		for (int step = 1; step <= maxStep; step++) {
			for (PersistenceMode mode : PersistenceMode.values()) {
				if (existsFileForType(backupPath, step, table, mode)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean existsFileForType(String backupPath, int step, Table table, PersistenceMode type) {

		String backupFilePath = getBackupFilePath(backupPath, step, table, type);
		Path path = Paths.get(backupFilePath);

		return Files.exists(path);
	}

	private String getPathPrefix(String backupPath, int step, Table table) {
		return backupPath + File.separator + step + File.separator
				+ table.myPackage.name + File.separator + table.originalName;
	}
}
