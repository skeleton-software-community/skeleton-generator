package org.skeleton.generator.repository.dao.datasource.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.exception.ReadBackupFailureException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupReader;
import org.skeleton.generator.repository.file.impl.TextDelimitedFileManager;
import org.skeleton.generator.repository.file.interfaces.FileManager;
import org.skeleton.generator.util.jdbc.JdbcHelper;


public class TextDelimitedFileBackupReader implements BackupReader {

	/*
	 * properties
	 */
	private FileManager backupFileManager;
	private String backupFilePath;
	private Table table;
	
	/*
	 * constructor
	 */
	public TextDelimitedFileBackupReader(Table table, String backupFilePath) {
		this.table = table;
		this.backupFileManager = new TextDelimitedFileManager(table.getInsertColumnList().size());
		this.backupFilePath = backupFilePath;
	}
	
	public List<Object[]> readBackupArgs()  {
		try {
			List<String[]> stringArgsList = backupFileManager.readData(backupFilePath);
			List<Object[]> argsList = new ArrayList<>();
			
			for (String[] stringArgs:stringArgsList) {
				argsList.add(getInsertAgrs(table, stringArgs));
			}
			return argsList;
			
		} catch (IOException | InvalidFileException e) {
			throw new ReadBackupFailureException("failed to read backup", e);
		}
	}

	private Object[] getInsertAgrs(Table table, String[] args) {
		List<Object> result = new ArrayList<Object>();
		
		for (int i=0;i<table.getInsertColumnList().size();i++) {
			result.add(JdbcHelper.getObjectFromString(table.getInsertColumnList().get(i).dataType, args[i]));
		}
		return result.toArray();
	}
}
