package org.skeleton.generator.bl.services.impl;

import org.skeleton.generator.bc.persistence.interfaces.ProjectMetaDataPersistenceHandler;
import org.skeleton.generator.bl.services.interfaces.ProjectLoader;
import org.skeleton.generator.bl.services.interfaces.ProjectMetaDataService;
import org.skeleton.generator.model.metadata.ColumnMetaData;
import org.skeleton.generator.model.metadata.PackageMetaData;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.metadata.TableMetaData;
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
	ProjectMetaDataPersistenceHandler projectMetaDataPersistenceHandler;
	
	
	@Override
	public ProjectMetaData loadProjectMetaData(String folderPath) {
		logger.info("start reading meta data");
		ProjectMetaData projectMetaData = projectMetaDataPersistenceHandler.loadProjectMetaData(folderPath);
		logger.info("end reading meta data");
		
		return projectMetaData;
	}
	
	
	
	

	@Override
	public void insertPackageMetaData(PackageMetaData packageMetaData, int index, ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertTableMetaData(TableMetaData tableMetaData, int index, PackageMetaData packageMetaData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertColumnMetaData(ColumnMetaData columnMetaData, int index, TableMetaData tableMetaData) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		logger.info("start persisting meta data");
		projectMetaDataPersistenceHandler.persistProjectMetaData(projectMetaData);
		logger.info("end persisting meta data");
	}
	
	@Override
	public void initProjectMetaData(ProjectMetaData projectMetaData) {
		
		logger.info("start initializing project");
		projectMetaDataPersistenceHandler.initProjectMetaData(projectMetaData);
		logger.info("end initializing meta data");
		
		logger.info("start persisting meta data");
		projectMetaDataPersistenceHandler.persistProjectMetaData(projectMetaData);
		logger.info("end persisting meta data");
	}
	
}
