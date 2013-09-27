package com.skeleton.generator.command;

import java.io.File;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.skeleton.generator.bl.command.jdbc.interfaces.InputSourceProvider;
import com.skeleton.generator.bl.services.impl.TablePopulatorFactory;
import com.skeleton.generator.bl.services.interfaces.ProjectLoader;
import com.skeleton.generator.bl.services.interfaces.TablePopulator;
import com.skeleton.generator.exception.BackupFileNotFoundException;
import com.skeleton.generator.model.om.Package;
import com.skeleton.generator.model.om.Project;
import com.skeleton.generator.model.om.Table;

public class DatabasePopulator {

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
				
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				project = projectLoader.loadProject(folderPath);
				
				logger.info("loading project " + project.projectName + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start populating database");
				
				DataSource dataSource = (BasicDataSource)appContext.getBean("projectDataSource");
				InputSourceProvider inputSourceProvider = (InputSourceProvider)appContext.getBean("inputSourceProvider");
				
				for (Package myPackage:project.model.packageList) {
					logger.info("start populating package : " + myPackage.name);
					
					for (Table table:myPackage.tableList) {
						logger.info("start populating table : " + table.name);
						
						try {
							TablePopulator tablePopulator = TablePopulatorFactory.buildTablePopulator(table, dataSource, inputSourceProvider);
							tablePopulator.populateTable();
							logger.info("populating table : " + table.name + " completed");
						} catch (BackupFileNotFoundException e) {
							logger.error(e.getMessage());
						}
					}
					logger.info("populating package " + myPackage.name + " completed");
				}
				logger.info("populating database completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}
