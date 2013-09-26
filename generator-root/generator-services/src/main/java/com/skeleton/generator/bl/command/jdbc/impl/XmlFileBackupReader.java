package com.skeleton.generator.bl.command.jdbc.impl;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.skeleton.generator.bl.command.jdbc.interfaces.BackupReader;
import com.skeleton.generator.bl.command.jdbc.interfaces.InputSourceProvider;
import com.skeleton.generator.exception.DataSourceNotFoundException;
import com.skeleton.generator.exception.ReadBackupFailureException;
import com.skeleton.generator.model.om.Table;

public class XmlFileBackupReader implements BackupReader {
	
	/*
	 * properties
	 */
	private String backupFilePath;
	private Table table;
	private XmlFileSourceAndScriptReader xmlFileScriptAndSourceReader;
	private InputSourceProvider inputSourceProvider;
	private SourceAndScriptBackupReader sourceAndScriptBackupReader;
	
	/*
	 * constructor
	 */
	public XmlFileBackupReader(Table table, String backupFilePath, InputSourceProvider inputSourceProvider) {
		this.table = table;
		this.backupFilePath = backupFilePath;
		this.xmlFileScriptAndSourceReader = new XmlFileSourceAndScriptReader();
		this.inputSourceProvider = inputSourceProvider;
	}

	@Override
	public List<Object[]> readBackupArgs() throws ReadBackupFailureException {

		SourceAndScript sourceAndScript;
		DataSource inputSource;
		
		try {
			sourceAndScript = xmlFileScriptAndSourceReader.readScript(backupFilePath);
		} catch (IOException e) {
			throw new ReadBackupFailureException("Failed to read source and script for table : " + table.name,e);
		}
	
		try {
			inputSource = inputSourceProvider.getDataSource(sourceAndScript.getSource());
		} catch (DataSourceNotFoundException e) {
			throw new ReadBackupFailureException("Invalid backup source for table : " + table.name, e);			
		}
		
		sourceAndScriptBackupReader = new SourceAndScriptBackupReader(table, sourceAndScript.getScript(), inputSource);
		
		return sourceAndScriptBackupReader.readBackupArgs();

	}

}
