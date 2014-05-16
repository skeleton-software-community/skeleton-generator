package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.repository.dao.jdbc.impl.JdbcRawCommand;
import org.skeleton.generator.repository.file.impl.SimpleScriptFileReaderImpl;
import org.skeleton.generator.repository.file.interfaces.SimpleScriptFileReader;
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
