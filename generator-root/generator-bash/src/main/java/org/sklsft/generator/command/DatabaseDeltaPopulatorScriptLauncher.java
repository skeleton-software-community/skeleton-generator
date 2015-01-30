package org.sklsft.generator.command;

import java.io.File;

import javax.sql.DataSource;

import org.sklsft.generator.bl.services.interfaces.DatabaseUpdater;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;
import org.sklsft.generator.repository.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to generate a script to populate a database with only 
 * new user data.<br/>
 * Argument required : 
 * <li>the workspace folder where the "data-model" folder will be detected
 * <li>the database name that must be declared in /data-model/CONTEXT/datasource-context.xml
 * Depending on the meta data that is going to be read, the main method will :
 * <ul>
 * <li>load the project representation
 * <li>choose the files in /data-model/BACKUP/ necessary to update the data of the database : 
 *   <ul>
 *     <li> data to populate a whole existing table (only txt present) => all the dependent tables are also populated
 *     <li> data to update the value of tables (xml update present for an structure updated table)
 *     <li> data to add to existing tables (txt file)
 *   </ul>
 * <li>generate the script which contains : 
 *   <ul>
 *     <li> data delete for table to re-populate
 *     <li> data insert or update depending on the previous analyse.  
 *   </ul>
 * <li>generate a summary of the instructions in the SQL script. 
 * 
 * @author Michaël Fernandez
 *
 */
public class DatabaseDeltaPopulatorScriptLauncher {
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabaseUpdaterLauncher.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	/**
	 * 
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
	 * @param args 1->data base name
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			throw new IllegalArgumentException("Path and datasource are Mandatory");
		}
		String workspacePath = args[0];
		String databaseName = args[1];
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml",sourcePath + File.separator + DATASOURCE_CONTEXT_FILE);){
			logger.info("Context loaded");
			
			Project project;
			
			try {
				logger.info("start loading project");
				
				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				
				ProjectMetaData projectMetaData = projectMetaDataService.loadProjectMetaData(workspacePath);
				project = projectLoader.loadProject(projectMetaData);
				
				logger.info("loading project " + project.projectName + " completed");
					
				OutputDataSourceProvider outputDataSourceProvider = appContext.getBean(OutputDataSourceProvider.class);
				DataSource dataSource = outputDataSourceProvider.getDataSource(databaseName);
				
				InputDataSourceProvider inputDataSourceProvider = appContext.getBean(InputDataSourceProvider.class);
				
				DatabaseUpdater databaseUpdater =  appContext.getBean(DatabaseUpdater.class);
				databaseUpdater.generatePopulationUpdateScript(dataSource, databaseName, inputDataSourceProvider, project);

			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
	

}
