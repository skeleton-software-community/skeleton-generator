package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.bc.command.jdbc.impl.SimpleScriptCommand;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.SimpleScriptFileReader;


public class TableBuilder {

	
	
	/*
	 * properties
	 */
	private Table table;
	private DataSource dataSource;
	private int step;
	
	/*
	 * constructor
	 */
	public TableBuilder(Table table, DataSource dataSource, int step) {
		this.table = table;
		this.dataSource = dataSource;
		this.step = step;
	}
	

	public void buildTable() throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = table.myPackage.model.project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + step + File.separator + table.myPackage.name + File.separator + table.originalName + ".sql";
		SimpleScriptFileReader scriptFileReader = new SimpleScriptFileReader(scriptFilePath);	
		String script = scriptFileReader.readScript();
			
		new SimpleScriptCommand(dataSource, script).execute();
	}
}
