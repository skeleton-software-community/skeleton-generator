package org.sklsft.generator.bash.launcher;

import org.sklsft.generator.bc.file.executor.FileWriteCommandTree;
import org.sklsft.generator.bl.services.interfaces.CodeGenerator;
import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.DatabaseEngine;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.SkeletonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * This class can be launched to initialize a project<br/>
 * For that, arguments are required to populate a {@link ProjectMetaData}<br/>
 * In your workspace folder, a data-model folder will be created with :
 * <li>your skeleton.xml file that contains your project meta-data
 * <li>a folder /data-model/CONTEXT/ with a template of your datasource-context.xml file for init and population
 * <li>your project configuration depending on the skeleton that you want
 * 
 * @author Nicolas Thibault
 *
 */
public class ProjectInitializerLauncher {
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectInitializerLauncher.class);
	
	
	/**
	 * main method to be executed
	 * @param args 0->the workspace folder where to put the "data-model" folder
	 * @param args 1->the domain name of your project (org.foo for example)
	 * @param args 2->your project name(myproject for example)
	 * @param args 3->the skeleton type of your project @see SkeletonType
	 * @param args 4->the database engine to be used @see DatabaseEngine
	 * @param args 5->your database name (MYDATABASE for example)
	 * @param args 6->your database hostname
	 * @param args 7->your database port
	 * @param args 8->your database connection username
	 * @param args 9->your database connection password
	 * @param args 10->a boolean string representation (True/False) to indicate whereas you want to activate hibernate auditing functionnality or not (envers)
	 */
	public static void main(String[] args) {
				
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-bash.xml");){
			logger.info("Context loaded");
			
			ProjectMetaData projectMetaData = buildProjectMetaData(args);
			Project project;
			
			CodeGenerator codeWriter = appContext.getBean(CodeGenerator.class);
			
			try {
				logger.info("start loading project");
				
				ProjectLoader projectLoader = appContext.getBean(ProjectLoader.class);
				project = projectLoader.loadProject(projectMetaData);
				
				logger.info("loading project " + project.projectName + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start persisting project");
				
				ProjectMetaDataService projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				projectMetaDataService.initProjectMetaData(projectMetaData);				
				
				logger.info("persisting project completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start copying resources");
				
				FileWriteCommandTree tree = codeWriter.buildFileImportTree(project);
				codeWriter.generateCode(tree);
				
				logger.info("copying resources completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start writing configuration");
				
				FileWriteCommandTree tree = codeWriter.buildConfigurationTree(project);
				codeWriter.generateCode(tree);
				
				logger.info("writing configuration completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}


	private static ProjectMetaData buildProjectMetaData(String[] args) {
		
		if (args.length < 11) {
			throw new IllegalArgumentException("11 arguments are mandatory");
		}
		
		ProjectMetaData projectMetaData = new ProjectMetaData();
		projectMetaData.setPersistenceMode(PersistenceMode.XML);
		projectMetaData.setWorkspaceFolder(args[0]);
		projectMetaData.setDomainName(args[1]);
		projectMetaData.setProjectName(args[2]);
		projectMetaData.setSkeletonType(SkeletonType.valueOf(args[3]));
		projectMetaData.setDatabaseEngine(DatabaseEngine.valueOf(args[4]));
		projectMetaData.setDatabaseName(args[5]);
		projectMetaData.setDatabaseDNS(args[6]);
		projectMetaData.setDatabasePort(args[7]);
		projectMetaData.setDatabaseUserName(args[8]);
		projectMetaData.setDatabasePassword(args[9]);
		projectMetaData.setAudited(Boolean.valueOf(args[10]));
		
		return projectMetaData;
	}
}
