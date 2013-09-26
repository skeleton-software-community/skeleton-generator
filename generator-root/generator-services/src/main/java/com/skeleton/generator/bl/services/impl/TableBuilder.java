package com.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.skeleton.generator.bl.command.jdbc.impl.SimpleScriptCommand;
import com.skeleton.generator.bl.command.jdbc.impl.SimpleScriptFileReader;
import com.skeleton.generator.exception.InvalidFileException;
import com.skeleton.generator.model.om.Table;

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
