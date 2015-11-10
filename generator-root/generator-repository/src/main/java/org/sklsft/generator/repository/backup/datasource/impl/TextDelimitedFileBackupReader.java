package org.sklsft.generator.repository.backup.datasource.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.file.impl.CsvFileParserImpl;
import org.sklsft.generator.repository.backup.file.interfaces.CsvFileParser;
import org.sklsft.generator.repository.util.JdbcUtil;


public class TextDelimitedFileBackupReader implements BackupArgumentReader {

	/*
	 * properties
	 */
	private CsvFileParser csvFileParser;
	private Table table;
	
	/*
	 * constructor
	 */
	public TextDelimitedFileBackupReader(Table table) {
		this.csvFileParser = new CsvFileParserImpl(StandardCharsets.UTF_8);
		this.table = table;
	}
	
	public BackupCommandArguments readBackupArgs(String backupFilePath)  {
		BackupCommandArguments result = new BackupCommandArguments();
		result.setArguments(readArgs(backupFilePath));
		result.setType(readType());
		return result;
	}
	
	private PopulateCommandType readType() {
		return PopulateCommandType.INSERT; //TODO put type in csv header
	}

	private List<Object[]> readArgs(String backupFilePath){
		try {
			List<String[]> stringArgsList = csvFileParser.readData(backupFilePath);
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
			result.add(JdbcUtil.getObjectFromString(table.getInsertColumnList().get(i).dataType, args[i]));
		}
		return result.toArray();
	}
}
