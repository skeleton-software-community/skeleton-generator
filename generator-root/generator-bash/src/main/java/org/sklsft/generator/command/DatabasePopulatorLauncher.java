package org.sklsft.generator.command;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.bl.services.impl.BackupPostExecutionChecker;
import org.sklsft.generator.bl.services.impl.BackupPreExecutionChecker;
import org.sklsft.generator.bl.services.interfaces.DatabasePopulator;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.backup.check.BackupPlanPostExecutionWarning;
import org.sklsft.generator.model.backup.check.BackupPlanPreExecutionWarning;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;
import org.sklsft.generator.repository.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to populate your database<br/>
 * Argument required : 
 * <li>the workspace folder where the "data-model" folder will be detected
 * <li>the database name that must be declared in /data-model/CONTEXT/datasource-context.xml
 * Optional argument : if you want to restrict the tables to populate<br/>
 * Depending on the meta data that is going to be read, the main method will :
 * <li>load the project representation
 * <li>read the scripts/csv files in /data-model/BACKUP/ and populate the tables with this data
 * the population of the tables uses generated stored procedures (insert by code, update by code)<br/>
 * two kinds of file can be used to fetch data
 * <li>a $ separated file (.txt)
 * <li>a xml file representing a {@link SourceAndScript} where you mention a datasource and a script to read (xsd is given in /data-model/BACKUP/backup-1.0.xsd)
 * in a xml file, you can mention every datasource that is available in the {@link InputDataSourceProvider} inputSourceProvider that is declared in your data-model/CONTEXT/datasource-context.xml file<br/>
 * 
 * @author Nicolas Thibault
 *
 */
public class DatabasePopulatorLauncher {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulatorLauncher.class);
	
	private static final String DATASOURCE_CONTEXT_FILE ="CONTEXT" + File.separator + "datasource-context.xml";
	
	/**
	 * 
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
	 * @param args 1(optional)->a list of semicolon separated table names if you want to restrict the population with this list
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			throw new IllegalArgumentException("Path and datasource are Mandatory");
		}
		String workspacePath = args[0];
		String databaseName = args[1];
		
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
				
				OutputDataSourceProvider outputDataSourceProvider = appContext.getBean(OutputDataSourceProvider.class);
				DataSource dataSource = outputDataSourceProvider.getDataSource(databaseName);
				
				InputDataSourceProvider inputDataSourceProvider = appContext.getBean(InputDataSourceProvider.class);
				
				BackupPreExecutionChecker preChecker = appContext.getBean(BackupPreExecutionChecker.class);
				logger.info("Checking backup plan before execution...");
				List<BackupPlanPreExecutionWarning> preExecutionWarnings = preChecker.checkPlan(inputDataSourceProvider, project, tables);
				logger.info("plan pre-execution check finished");
				
				BackupCheckPrompter prompter = new BackupCheckPrompter();
				prompter.printPreExecutionWarnings(preExecutionWarnings);
				prompter.promptForConfirmation();
				
				
				DatabasePopulator databasePopulator = appContext.getBean(DatabasePopulator.class);
				databasePopulator.populateDatabase(dataSource, inputDataSourceProvider, project, tables);
				
				BackupPostExecutionChecker postChecker = appContext.getBean(BackupPostExecutionChecker.class);
				logger.info("Checking backup plan after execution...");
				List<BackupPlanPostExecutionWarning> postExecutionWarnings = postChecker.checkPlan(dataSource, project, tables);
				logger.info("plan post-execution check finished");
				
				prompter.printPostExecutionWarnings(postExecutionWarnings);
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
	
	private static Set<String> extractTables(String[] args) {
		
		Set<String> tables = null;
		
		if (args.length > 2) {
			String tablesArg = args[2];
			String[] tableTokens = tablesArg.split(";");
			tables = new HashSet<String>();
			for (String table:tableTokens) {
				tables.add(table);
			}
		}
		
		return tables;
	}
}
