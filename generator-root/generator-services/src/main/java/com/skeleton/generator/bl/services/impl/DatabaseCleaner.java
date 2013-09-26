package com.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.skeleton.generator.bl.command.jdbc.impl.SimpleScriptCommand;
import com.skeleton.generator.bl.command.jdbc.impl.SimpleScriptFileReader;
import com.skeleton.generator.exception.InvalidFileException;
import com.skeleton.generator.model.om.Project;

public class DatabaseCleaner {

	private static final String SCRIPT_FOLDER = "SQL";
	
	/*
	 * properties
	 */
	private Project porject;
	private DataSource dataSource;
	
	/*
	 * constructor
	 */
	public DatabaseCleaner(Project project, DataSource dataSource) {
		this.porject = project;
		this.dataSource = dataSource;
		
	}
	

	public void cleanDatabase() throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = porject.sourceFolder + File.separator + SCRIPT_FOLDER + File.separator + "MAIN.sql";
		SimpleScriptFileReader scriptFileReader = new SimpleScriptFileReader(scriptFilePath);	
		String script = scriptFileReader.readScript();
			
		new SimpleScriptCommand(dataSource, script).execute();
	}
}
