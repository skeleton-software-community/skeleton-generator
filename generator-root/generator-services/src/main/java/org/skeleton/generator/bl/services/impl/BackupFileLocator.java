package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.om.Table;

public class BackupFileLocator {
	
	private static final String BACKUP_FOLDER = "BACKUP";

	public PersistenceMode resolvePersistenceMode(int step, Table table){
		if(existsFileForType(PersistenceMode.XML, step, table)) return PersistenceMode.XML;
		else if(existsFileForType(PersistenceMode.CSV, step, table)) return PersistenceMode.CSV;
		else throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
		
	}
	
	private boolean existsFileForType(PersistenceMode type, int step, Table table){
		Path path;
		String backupFilePath;
		
		backupFilePath =  getBackupFilePath(step, table, type);
		path = Paths.get(backupFilePath);
		
		return Files.exists(path);
	}
	
	private String getPathPrefix(int step, Table table){
		return table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + step + File.separator + table.myPackage.name + File.separator + table.originalName;
	}
	
	public String getBackupFilePath(int step, Table table, PersistenceMode mode){
		return getPathPrefix(step, table) + mode.getExtension();
	}
}