package org.sklsft.generator.bl.services.impl;

import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.components.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.components.validation.ProjectMetaDataValidator;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;
import org.sklsft.generator.persistence.metadata.interfaces.ProjectMetaDataDao;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ProjectMetaDataDao projectMetaDataDao;
	
	@Autowired
	private ProjectMetaDataValidator projectMetaDataValidator;
	
	
	@Override
	public ProjectMetaData loadProjectMetaData(String folderPath) {
		logger.info("start reading meta data");
		ProjectMetaData projectMetaData = projectMetaDataDao.loadProjectMetaData(folderPath);
		logger.info("end reading meta data");
		
		return projectMetaData;
	}
	
	
	@Override
	public ProjectValidationReport validate(ProjectMetaData project) {
		logger.info("start validating meta data");
		ProjectValidationReport report = projectMetaDataValidator.validate(project);
		logger.info("end validating meta data");
		
		return report;
	}
	
	
	@Override
	public void initProjectMetaData(ProjectMetaData projectMetaData) {
		
		DatabaseHandler databaseHandler = DatabaseHandlerDiscovery.getDatabaseHandler(projectMetaData.getDatabaseEngine());
		projectMetaData.getDataSource().setDriverClassName(databaseHandler.getDriverClassName());
		projectMetaData.getDataSource().setUrl(databaseHandler.getUrl(projectMetaData.getDataSource()));
		projectMetaData.getDataSource().setDialect(databaseHandler.getDialect());
		
		logger.info("start initializing project");
		projectMetaDataDao.initProject(projectMetaData);
		logger.info("end initializing meta data");
		
		logger.info("start persisting meta data");
		projectMetaDataDao.persistProjectMetaData(projectMetaData);
		logger.info("end persisting meta data");
		
		logger.info("start persisting datasource context");
		projectMetaDataDao.persistDatasourceContext(projectMetaData);
		logger.info("end persisting datasource context");
	}
}
