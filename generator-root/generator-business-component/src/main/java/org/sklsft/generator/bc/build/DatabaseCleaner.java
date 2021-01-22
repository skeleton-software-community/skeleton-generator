package org.sklsft.generator.bc.build;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.repository.backup.file.interfaces.SimpleScriptFileReader;
import org.sklsft.generator.repository.build.JdbcRawCommand;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {
	
	@Inject
	private SimpleScriptFileReader scriptFileReader;	

	public void cleanDatabase(DataSource dataSource, Project project) throws IOException, InvalidFileException, SQLException {
		
		String scriptFilePath = project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(project.databaseEngine) + File.separator + "MAIN.sql";
		String script = scriptFileReader.readScript(scriptFilePath);
			
		new JdbcRawCommand(dataSource, script).execute();
	}
}
