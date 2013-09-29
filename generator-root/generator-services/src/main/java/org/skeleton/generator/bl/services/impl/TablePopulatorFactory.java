package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.skeleton.generator.bl.services.interfaces.TablePopulator;
import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;


public class TablePopulatorFactory {
	
	private static final String BACKUP_FOLDER = "BACKUP";
	
	
	public static TablePopulator buildTablePopulator(Table table, DataSource dataSource,InputSourceProvider inputSourceProvider) throws BackupFileNotFoundException {
		
		Path path;
		String backupFilePath;
		
		backupFilePath = table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + table.myPackage.name + File.separator + table.originalName + ".xml";
		path = Paths.get(backupFilePath);
		
		if (Files.exists(path)){
			return new XmlTablePopulator(table, dataSource, backupFilePath, inputSourceProvider);
		}
		
		backupFilePath = table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + table.myPackage.name + File.separator + table.originalName + ".txt";
		path = Paths.get(backupFilePath);
		
		if (Files.exists(path)){
			return new TextDelimitedTablePopulator(table, dataSource, backupFilePath);
		}
		
		throw new BackupFileNotFoundException("No backup file found for table : " + table.name);
	}

}
