package org.skeleton.generator.repository.dao.datasource.impl;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.exception.DataSourceNotFoundException;
import org.skeleton.generator.exception.ReadBackupFailureException;
import org.skeleton.generator.model.backup.BackupCommandArguments;
import org.skeleton.generator.model.backup.PopulateCommandType;
import org.skeleton.generator.model.backup.SourceAndScript;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupArgumentReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;

/**
 * Implementation of a {@link BackupArgumentReader} that uses a path to a Xml backup file, a {@link InputSourceProvider} and a {@link Table} for meta-data
 * the processing will use a {@link SourceAndScriptBackupReader} by converting the xml backup file to a {@link SourceAndScript} to extract a {@link DataSource} and a script
 * @author Nicolas Thibault
 *
 */
public class XmlFileBackupReader implements BackupArgumentReader {
	
	/*
	 * properties
	 */
	private InputSourceProvider inputSourceProvider;
	private XmlFileSourceAndScriptParser xmlFileSourceAndScriptParser;
	private SourceAndScriptBackupReader sourceAndScriptBackupReader;
	
	/*
	 * constructor
	 */
	public XmlFileBackupReader(InputSourceProvider inputSourceProvider) {
		this.xmlFileSourceAndScriptParser = new XmlFileSourceAndScriptParser();
		this.inputSourceProvider = inputSourceProvider;
	}

	@Override
	public BackupCommandArguments readBackupArgs(Table table, String backupFilePath) throws ReadBackupFailureException {

		BackupCommandArguments result = new BackupCommandArguments();
		result.setArguments(readArguments(table, backupFilePath));
		result.setType(readType());
		return result;

	}

	private PopulateCommandType readType() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Object[]> readArguments(Table table, String backupFilePath) {
		SourceAndScript sourceAndScript;
		DataSource inputSource;
		
		try {
			sourceAndScript = xmlFileSourceAndScriptParser.parse(backupFilePath);
		} catch (IOException e) {
			throw new ReadBackupFailureException("Failed to read source and script for table : " + table.name,e);
		}
	
		try {
			inputSource = inputSourceProvider.getDataSource(sourceAndScript.getSource());
		} catch (DataSourceNotFoundException e) {
			throw new ReadBackupFailureException("Invalid backup source for table : " + table.name, e);			
		}
		
		sourceAndScriptBackupReader = new SourceAndScriptBackupReader(inputSource);
		
		return sourceAndScriptBackupReader.readBackupArgs(table, backupFilePath);
	}

}
