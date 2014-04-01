package org.skeleton.generator.command;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.skeleton.generator.bl.services.impl.TablePopulatorFactory;
import org.skeleton.generator.bl.services.interfaces.ProjectLoader;
import org.skeleton.generator.bl.services.interfaces.ProjectMetaDataService;
import org.skeleton.generator.bl.services.interfaces.TablePopulator;
import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.backup.SourceAndScript;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to populate your database<br/>
 * Argument required : the workspace folder where the "data-model" folder will be detected<br/>
 * Optional argument : if you want to restrict the tables to populate<br/>
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation
 * <li>read the scripts/csv files in /data-model/BACKUP/ and populate the tables with this data
 * the population of the tables uses generated stored procedures (insert by code)<br/>
 * two kinds of file can be used to fetch data
 * <li>a $ separated file (.txt)
 * <li>a xml file representing a {@link SourceAndScript} where you mention a datasource and a script to read (xsd is given in /data-model/BACKUP/backup-1.0.xsd)
 * in a xml file, you can mention every datasource that is available in the {@link InputSourceProvider} that is declared in your data-model/CONTEXT/datasource-context.xml file<br/>
 * 
 * @author Nicolas Thibault
 *
 */
public class DatabasePopulator {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulator.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	/**
	 * 
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
	 * @param args 1(optional)->a list of semicolon separated table names if you want to restrict the population with this list
	 */
	public static void main(String[] args) {
		
		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String workspacePath = args[0];
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		Set<String> tables = extractTables(args);
		
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
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start populating database");
				
				DataSource dataSource = (BasicDataSource)appContext.getBean("projectDataSource");
				InputSourceProvider inputSourceProvider = (InputSourceProvider)appContext.getBean("inputSourceProvider");
				
				for (Package myPackage:project.model.packages) {
					logger.info("start populating package : " + myPackage.name);
					
					for (Table table:myPackage.tables) {
						
						if (tables == null || tables.contains(table.originalName)) {
						
							logger.info("start populating table : " + table.name);
							
							try {
								TablePopulator tablePopulator = TablePopulatorFactory.buildTablePopulator(table, dataSource, inputSourceProvider);
								tablePopulator.populateTable();
								logger.info("populating table : " + table.name + " completed");
							} catch (BackupFileNotFoundException e) {
								logger.error(e.getMessage());
							}
						} else {
							logger.info("table : " + table.name + " skipped");
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
	
	private static Set<String> extractTables(String[] args) {
		
		Set<String> tables = null;
		
		if (args.length > 1) {
			String tablesArg = args[1];
			String[] tableTokens = tablesArg.split(";");
			tables = new HashSet<String>();
			for (String table:tableTokens) {
				tables.add(table);
			}
		}
		
		return tables;
	}
}
