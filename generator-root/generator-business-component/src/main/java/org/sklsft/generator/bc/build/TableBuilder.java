package org.sklsft.generator.bc.build;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.repository.backup.file.impl.SimpleScriptFileReaderImpl;
import org.sklsft.generator.repository.backup.file.interfaces.SimpleScriptFileReader;
import org.sklsft.generator.repository.build.JdbcRawCommand;


public class TableBuilder {

	/*
	 * properties
	 */
	private BasicDataSource dataSource;
	private SimpleScriptFileReader scriptFileReader;
	private String scriptRootPath;
	
	/*
	 * constructor
	 */
	public TableBuilder(Project project, BasicDataSource dataSource) {
		this.dataSource = dataSource;
		this.scriptFileReader = new SimpleScriptFileReaderImpl();
		scriptRootPath = project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(dataSource);
	}
	

	public void buildTable(Table table, int step) throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = scriptRootPath + File.separator + step + File.separator + table.myPackage.name.toUpperCase().replace(".", File.separator) + File.separator + table.originalName + ".sql";
		
		String script = scriptFileReader.readScript(scriptFilePath);
			
		new JdbcRawCommand(dataSource, script).execute();
	}
}
