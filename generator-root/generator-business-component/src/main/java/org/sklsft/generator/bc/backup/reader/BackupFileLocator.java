package org.sklsft.generator.bc.backup.reader;

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

	private static final String BACKUP_FOLDER = "BACKUP";

	public PersistenceMode resolvePersistenceMode(int step, Table table) {

		PersistenceMode mode = resolvePersistenceModeOrNull(step, table);

		if (mode == null) {
			throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
		} else {
			return mode;
		}
	}

	public PersistenceMode resolvePersistenceModeOrNull(int step, Table table) {
		if (existsFileForType(PersistenceMode.CMD, step, table)) {
			return PersistenceMode.CMD;
		} else if (existsFileForType(PersistenceMode.XML, step, table)) {
			return PersistenceMode.XML;
		} else if (existsFileForType(PersistenceMode.CSV, step, table)) {
			return PersistenceMode.CSV;
		} else {
			return null;
		}
	}

	public String getBackupFilePath(int step, Table table, PersistenceMode mode) {
		return getPathPrefix(step, table) + mode.getExtension();
	}

	public String getBackupFilePath(int step, Table table) {
		PersistenceMode mode = resolvePersistenceMode(step, table);
		return getPathPrefix(step, table) + mode.getExtension();
	}

	public boolean existsFileForTable(Table table, int maxStep) {
		for (int step = 1; step <= maxStep; step++) {
			for (PersistenceMode mode : PersistenceMode.values()) {
				if (existsFileForType(mode, step, table)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean existsFileForType(PersistenceMode type, int step, Table table) {

		String backupFilePath = getBackupFilePath(step, table, type);
		Path path = Paths.get(backupFilePath);

		return Files.exists(path);
	}

	private String getPathPrefix(int step, Table table) {
		return table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + step + File.separator
				+ table.myPackage.name + File.separator + table.originalName;
	}
}
