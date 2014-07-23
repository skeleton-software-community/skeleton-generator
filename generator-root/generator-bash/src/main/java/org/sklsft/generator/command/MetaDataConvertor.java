package org.sklsft.generator.command;

import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * As the csv representation of your project meta data will no longer be supported for next release<br/>
 * This class aims at converting your meta data representation from csv to xml<br/>
 * Argument required : the workspace folder where the "data-model" folder will be detected<br/>
 * @author Nicolas Thibault
 *
 */
public class MetaDataConvertor {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MetaDataConvertor.class);
	
	
	/**
	 * main method to be executed
	 * @param args 0->the workspace folder where the "data-model" folder will be detected
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
