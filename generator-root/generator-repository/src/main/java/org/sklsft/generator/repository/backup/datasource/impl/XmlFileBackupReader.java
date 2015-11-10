package org.sklsft.generator.repository.backup.datasource.impl;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.exception.DataSourceNotFoundException;
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;

/**
 * Implementation of a {@link BackupArgumentReader} that uses a path to a Xml backup file, a {@link InputDataSourceProvider} and a {@link Table} for meta-data
 * the processing will use a {@link SourceAndScriptBackupReader} by converting the xml backup file to a {@link SourceAndScript} to extract a {@link DataSource} and a script
 * @author Nicolas Thibault
 *
 */
public class XmlFileBackupReader implements BackupArgumentReader {
	
	/*
	 * properties
	 */
	private InputDataSourceProvider inputSourceProvider;
	private Table table;
	private XmlFileSourceAndScriptSimpleParser xmlFileSourceAndScriptParser;
	private SourceAndScriptBackupReader sourceAndScriptBackupReader;
	
	/*
	 * constructor
	 */
	public XmlFileBackupReader(InputDataSourceProvider inputSourceProvider, Table table) {
		this.xmlFileSourceAndScriptParser = new XmlFileSourceAndScriptSimpleParser();
		this.inputSourceProvider = inputSourceProvider;
		this.table = table;
		
	}

	@Override
	public BackupCommandArguments readBackupArgs(String backupFilePath) throws ReadBackupFailureException {

		SourceAndScript sourceAndScript;
		try {
			sourceAndScript = xmlFileSourceAndScriptParser.parse(backupFilePath);
		} catch (IOException e) {
			throw new ReadBackupFailureException("Failed to read source and script for table : " + table.name,e);
		}
		
		BackupCommandArguments result = new BackupCommandArguments();
		result.setArguments(readArguments(sourceAndScript));
		result.setType(sourceAndScript.getType());
		return result;

	}


	private List<Object[]> readArguments(SourceAndScript sourceAndScript) {
		
		DataSource inputSource;
	
		try {
			inputSource = inputSourceProvider.getDataSource(sourceAndScript.getSource());
		} catch (DataSourceNotFoundException e) {
			throw new ReadBackupFailureException("Invalid backup source for table : " + table.name, e);			
		}
		
		sourceAndScriptBackupReader = new SourceAndScriptBackupReader(inputSource, table);
		
		return sourceAndScriptBackupReader.readBackupArgs(sourceAndScript.getScript());
	}

}
