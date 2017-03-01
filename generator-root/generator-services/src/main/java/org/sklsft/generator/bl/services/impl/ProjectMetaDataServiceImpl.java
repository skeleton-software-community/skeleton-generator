package org.sklsft.generator.bl.services.impl;

import javax.inject.Inject;

import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.repository.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProjectMetaDataServiceImpl implements ProjectMetaDataService {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectLoader.class);

	/*
	 * properties injected by spring
	 */
	@Inject
	ProjectMetaDataDao projectMetaDataDao;
	
	
	@Override
	public ProjectMetaData loadProjectMetaData(String folderPath) {
		logger.info("start reading meta data");
		ProjectMetaData projectMetaData = projectMetaDataDao.loadProjectMetaData(folderPath);
		logger.info("end reading meta data");
		
		return projectMetaData;
	}
	
	
	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		logger.info("start persisting meta data");
		projectMetaDataDao.persistProjectMetaData(projectMetaData);
		logger.info("end persisting meta data");
	}
	
	
	@Override
	public void initProjectMetaData(ProjectMetaData projectMetaData) {
		
		logger.info("start initializing project");
		projectMetaDataDao.initProject(projectMetaData);
		logger.info("end initializing meta data");
		
		logger.info("start persisting meta data");
		projectMetaDataDao.persistProjectMetaData(projectMetaData);
		logger.info("end persisting meta data");
	}
}
