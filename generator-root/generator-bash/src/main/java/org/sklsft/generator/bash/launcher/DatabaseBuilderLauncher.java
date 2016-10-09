package org.sklsft.generator.bash.launcher;

import java.io.File;

import javax.sql.DataSource;

import org.sklsft.generator.bl.services.interfaces.DatabaseBuilder;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;
import org.sklsft.generator.repository.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to execute your database building<br/>
 * Argument required : 
 * <li>the workspace folder where the "data-model" folder will be detected
 * <li>the database name that must be declared in /data-model/CONTEXT/datasource-context.xml
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation
 * <li>clean the project database
 * <li>execute all the SQL files that have been previously generated to build your database
 * @author Nicolas Thibault
 *
 */
public class DatabaseBuilderLauncher {
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabaseBuilderLauncher.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	
	/**
	 * main method to be executed
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
	 * @param args 1->the database name, declared in /data-model/CONTEXT/datasource-context.xml
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			throw new IllegalArgumentException("Path and datasource are Mandatory");
		}
		String workspacePath = args[0];
		String databaseName = args[1];
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-bash.xml", sourcePath + File.separator + DATASOURCE_CONTEXT_FILE);){
			logger.info("Context loaded");
			
			Project project;
			
			try {
				logger.info("start loading project");
				
				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				
				ProjectMetaData projectMetaData = projectMetaDataService.loadProjectMetaData(workspacePath);
				project = projectLoader.loadProject(projectMetaData);
				
				logger.info("loading project " + project.projectName + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				OutputDataSourceProvider outputDataSourceProvider = appContext.getBean(OutputDataSourceProvider.class);
				DataSource dataSource = outputDataSourceProvider.getDataSource(databaseName);
				
				DatabaseBuilder databaseBuilder = appContext.getBean(DatabaseBuilder.class);
				databaseBuilder.buildDatabase(dataSource, project);
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}