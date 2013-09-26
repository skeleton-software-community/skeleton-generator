package com.skeleton.generator.bl.command.jdbc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.skeleton.generator.bl.command.jdbc.interfaces.BackupReader;
import com.skeleton.generator.bl.helper.query.QueryHelper;
import com.skeleton.generator.exception.InvalidFileException;
import com.skeleton.generator.exception.ReadBackupFailureException;
import com.skeleton.generator.model.om.Table;
import com.skeleton.generator.repository.file.impl.TextDelimitedFileManager;
import com.skeleton.generator.repository.file.interfaces.FileManager;

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
		this.backupFileManager = new TextDelimitedFileManager(QueryHelper.getInsertArgsNumber(table));
		this.backupFilePath = backupFilePath;
	}
	
	public List<Object[]> readBackupArgs()  {
		try {
			List<String[]> stringArgsList = backupFileManager.readData(backupFilePath);
			List<Object[]> argsList = new ArrayList<>();
			
			for (String[] stringArgs:stringArgsList) {
				argsList.add(QueryHelper.getInsertAgrs(table, stringArgs));
			}
			return argsList;
			
		} catch (IOException | InvalidFileException e) {
			throw new ReadBackupFailureException("failed to read backup", e);
		}
	}
}
