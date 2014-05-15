package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.bc.command.jdbc.impl.SimpleScriptCommand;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.repository.dao.datasource.impl.SimpleScriptFileReader;


public class DatabaseCleaner {
	
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
		
		String scriptFilePath = porject.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + "MAIN.sql";
		SimpleScriptFileReader scriptFileReader = new SimpleScriptFileReader(scriptFilePath);	
		String script = scriptFileReader.readScript();
			
		new SimpleScriptCommand(dataSource, script).execute();
	}
}
