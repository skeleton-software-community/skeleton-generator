package org.skeleton.generator.command;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.bl.services.interfaces.CodeWriter;
import org.skeleton.generator.bl.services.interfaces.ProjectLoader;
import org.skeleton.generator.bl.services.interfaces.ProjectMetaDataService;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CodeGenerator {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String folderPath = args[0];
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml");){
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
				logger.info("start executing code writing");
				
				CodeWriter codeWriter = appContext.getBean(CodeWriter.class);
				FileWriteCommandTree tree = codeWriter.buildFileWriteCommandTree(project);
				tree.launch();
				
				logger.info("executing code writing completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}
