package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.backup.command.impl.JdbcRawCommand;
import org.sklsft.generator.repository.backup.file.interfaces.SimpleScriptFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {
	
	@Autowired
	private SimpleScriptFileReader scriptFileReader;	

	public void cleanDatabase(DataSource dataSource, Project project) throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + "MAIN.sql";
		String script = scriptFileReader.readScript(scriptFilePath);
			
		new JdbcRawCommand(dataSource, script).execute();
	}
}
