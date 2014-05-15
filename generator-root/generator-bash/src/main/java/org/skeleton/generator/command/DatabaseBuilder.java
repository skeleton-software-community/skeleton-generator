package org.skeleton.generator.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.skeleton.generator.bl.services.impl.DatabaseCleaner;
import org.skeleton.generator.bl.services.impl.TableBuilder;
import org.skeleton.generator.bl.services.interfaces.ProjectLoader;
import org.skeleton.generator.bl.services.interfaces.ProjectMetaDataService;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to execute your database building<br/>
 * Argument required : the workspace folder where the "data-model" folder will be detected<br/>
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation
 * <li>clean the project database that must be set in /data-model/CONTEXT/datasource-context.xml
 * <li>execute all the SQL files that have been previously generated to build your database
 * in the datasource-context.xml file, your database is set as a bean named "projectDataSource" of class {@link org.apache.commons.dbcp.BasicDataSource}
 * @author Nicolas Thibault
 *
 */
public class DatabaseBuilder {
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	
	/**
	 * main method to be executed
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
	 */
	public static void main(String[] args) {
		
		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String workspacePath = args[0];
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml", sourcePath + File.separator + DATASOURCE_CONTEXT_FILE);){
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
			
			int maxStep = resolveMaxStep(project);
			
			try {
				logger.info("start cleaning database");
				DataSource dataSource = (BasicDataSource)appContext.getBean("projectDataSource");
				
				DatabaseCleaner databaseCleaner = new DatabaseCleaner(project, dataSource);
				databaseCleaner.cleanDatabase();
				
				logger.info("cleaning database completed");
				
				logger.info("start bulding database");
				
				for (int step = 1; step <= maxStep; step++) {
					
					logger.info("start bulding step " + step);
				
					for (Package myPackage:project.model.packages) {
						logger.info("start building package : " + myPackage.name);
						
						for (Table table:myPackage.tables) {
							logger.info("start building table : " + table.name);
							
							TableBuilder tableBuilder = new TableBuilder(table, dataSource, step);
							tableBuilder.buildTable();
							
							logger.info("building table : " + table.name + " completed");
						}
						logger.info("building package " + myPackage.name + " completed");
					}
					logger.info("bulding step " + step + " completed");
				}
				
				logger.info("bulding database completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}


	private static int resolveMaxStep(Project project) {
		int maxStep = 0;
		while (Files.exists(Paths.get(project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + String.valueOf(maxStep+1)))) {
			maxStep++;
		}
		return maxStep;
	}
}