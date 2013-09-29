package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.bc.command.jdbc.impl.SimpleScriptCommand;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.SimpleScriptFileReader;


public class TableBuilder {

	private static final String SCRIPT_FOLDER = "SQL";
	
	/*
	 * properties
	 */
	private Table table;
	private DataSource dataSource;
	
	/*
	 * constructor
	 */
	public TableBuilder(Table table, DataSource dataSource) {
		this.table = table;
		this.dataSource = dataSource;
		
	}
	

	public void buildTable() throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = table.myPackage.model.project.sourceFolder + File.separator + SCRIPT_FOLDER + File.separator + table.myPackage.name + File.separator + table.originalName + ".sql";
		SimpleScriptFileReader scriptFileReader = new SimpleScriptFileReader(scriptFilePath);	
		String script = scriptFileReader.readScript();
			
		new SimpleScriptCommand(dataSource, script).execute();
	}
}
