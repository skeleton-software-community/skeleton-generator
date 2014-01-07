package org.skeleton.generator.command;

import org.skeleton.generator.bl.services.interfaces.ProjectMetaDataService;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.util.metadata.PersistenceMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MetaDataConvertor {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MetaDataConvertor.class);
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("Path is Mandatory");
		}
		String folderPath = args[0];
		
		ProjectMetaData projectMetaData;
		ProjectMetaDataService projectMetaDataService;
		
		try(FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("classpath:applicationContext-generator-command.xml");){
			logger.info("Context loaded");
			
			try {
				logger.info("start loading project metadata");
				
				projectMetaDataService = appContext.getBean(ProjectMetaDataService.class);
				
				projectMetaData = projectMetaDataService.loadProjectMetaData(folderPath);
				
				logger.info("loading project metadata" + projectMetaData.getProjectName() + " completed");
					
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
			
			try {
				logger.info("start converting project");
				
				projectMetaData.setPersistenceMode(PersistenceMode.XML);
				projectMetaDataService.persistProjectMetaData(projectMetaData);
				
				logger.info("converting project completed");
				
			} catch (Exception e) {
				logger.error("failed", e);
				return;
			}
		}
	}
}
