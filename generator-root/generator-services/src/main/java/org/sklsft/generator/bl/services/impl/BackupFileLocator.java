package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.Table;
import org.springframework.stereotype.Component;

@Component
public class BackupFileLocator {

	private static final String BACKUP_FOLDER = "BACKUP";

	public PersistenceMode resolvePersistenceMode(int step, Table table) {
		if (existsFileForType(PersistenceMode.XML, step, table)) {
			return PersistenceMode.XML;
		} else if (existsFileForType(PersistenceMode.CSV, step, table)) {
			return PersistenceMode.CSV;
		} else {
			throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
		}
	}

	public String getBackupFilePath(int step, Table table, PersistenceMode mode) {
		return getPathPrefix(step, table) + mode.getExtension();
	}

	private boolean existsFileForType(PersistenceMode type, int step, Table table) {

		String backupFilePath = getBackupFilePath(step, table, type);
		Path path = Paths.get(backupFilePath);

		return Files.exists(path);
	}

	private String getPathPrefix(int step, Table table) {
		return table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + step + File.separator + table.myPackage.name + File.separator + table.originalName;
	}
}
