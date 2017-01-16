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

	public PersistenceMode resolvePersistenceMode(int step, Table table, String backupPath) {

		PersistenceMode mode = resolvePersistenceModeOrNull(step, table, backupPath);

		if (mode == null) {
			throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
		} else {
			return mode;
		}
	}

	public PersistenceMode resolvePersistenceModeOrNull(int step, Table table, String backupPath) {
		if (existsFileForType(PersistenceMode.CMD, step, table, backupPath)) {
			return PersistenceMode.CMD;
		} else if (existsFileForType(PersistenceMode.XML, step, table, backupPath)) {
			return PersistenceMode.XML;
		} else if (existsFileForType(PersistenceMode.CSV, step, table, backupPath)) {
			return PersistenceMode.CSV;
		} else {
			return null;
		}
	}

	public String getBackupFilePath(int step, Table table, PersistenceMode mode, String backupPath) {
		return getPathPrefix(step, table, backupPath) + mode.getExtension();
	}

	public String getBackupFilePath(int step, Table table, String backupPath) {
		PersistenceMode mode = resolvePersistenceMode(step, table, backupPath);
		return getPathPrefix(step, table, backupPath) + mode.getExtension();
	}

	public boolean existsFileForTable(Table table, int maxStep, String backupPath) {
		for (int step = 1; step <= maxStep; step++) {
			for (PersistenceMode mode : PersistenceMode.values()) {
				if (existsFileForType(mode, step, table, backupPath)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean existsFileForType(PersistenceMode type, int step, Table table, String backupPath) {

		String backupFilePath = getBackupFilePath(step, table, type, backupPath);
		Path path = Paths.get(backupFilePath);

		return Files.exists(path);
	}

	private String getPathPrefix(int step, Table table, String backupPath) {
		return backupPath + File.separator + step + File.separator
				+ table.myPackage.name + File.separator + table.originalName;
	}
}
