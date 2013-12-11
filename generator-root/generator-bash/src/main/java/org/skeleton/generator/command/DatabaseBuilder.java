package org.skeleton.generator.command;

import java.io.File;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class DatabaseBuilder {
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String folderPath = args[0];
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml",folderPath + File.separator + DATASOURCE_CONTEXT_FILE);){
			logger.info("Context loaded");
			
			Project project;
			
			try {
				logger.info("start loading project");
				
				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				
				ProjectMetaData projectMetaData = projectMetaDataService.loadProjectMetaData(folderPath);
				project = projectLoader.loadProject(projectMetaData);
				
				logger.info("loading project " + project.projectName + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start cleaning database");
				DataSource dataSource = (BasicDataSource)appContext.getBean("projectDataSource");
				
				DatabaseCleaner databaseCleaner = new DatabaseCleaner(project, dataSource);
				databaseCleaner.cleanDatabase();
				
				logger.info("cleaning database completed");
				
				for (Package myPackage:project.model.packageList) {
					logger.info("start building package : " + myPackage.name);
					
					for (Table table:myPackage.tableList) {
						logger.info("start building table : " + table.name);
						
						TableBuilder tableBuilder = new TableBuilder(table, dataSource);
						tableBuilder.buildTable();
						
						logger.info("building table : " + table.name + " completed");
					}
					logger.info("building package " + myPackage.name + " completed");
				}
				logger.info("bulding database completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}