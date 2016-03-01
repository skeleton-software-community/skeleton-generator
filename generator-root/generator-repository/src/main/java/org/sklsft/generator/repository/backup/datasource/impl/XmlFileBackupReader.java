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
	private XmlFileSourceAndScriptSimpleParser xmlFileSourceAndScriptParser;
	private SourceAndScriptBackupReader sourceAndScriptBackupReader;
	
	/*
	 * constructor
	 */
	public XmlFileBackupReader(InputDataSourceProvider inputSourceProvider) {
		this.xmlFileSourceAndScriptParser = new XmlFileSourceAndScriptSimpleParser();
		this.inputSourceProvider = inputSourceProvider;
		
	}

	@Override
	public BackupCommandArguments readBackupArgs(String backupFilePath) throws ReadBackupFailureException {

		SourceAndScript sourceAndScript;
		try {
			sourceAndScript = xmlFileSourceAndScriptParser.parse(backupFilePath);
		} catch (IOException e) {
			throw new ReadBackupFailureException("Failed to read source and script",e);
		}
		
		BackupCommandArguments result = new BackupCommandArguments();
		result.setArguments(readArguments(sourceAndScript));
		result.setType(sourceAndScript.getType());
		result.setArgumentsTyped(true);
		return result;

	}


	private List<Object[]> readArguments(SourceAndScript sourceAndScript) {
		
		DataSource inputSource;
	
		try {
			inputSource = inputSourceProvider.getDataSource(sourceAndScript.getSource());
		} catch (DataSourceNotFoundException e) {
			throw new ReadBackupFailureException("Invalid backup source", e);			
		}
		
		sourceAndScriptBackupReader = new SourceAndScriptBackupReader(inputSource);
		
		return sourceAndScriptBackupReader.readBackupArgs(sourceAndScript.getScript());
	}

}
