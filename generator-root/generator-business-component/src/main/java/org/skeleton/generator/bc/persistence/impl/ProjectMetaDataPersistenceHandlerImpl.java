package org.skeleton.generator.bc.persistence.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.skeleton.generator.bc.persistence.interfaces.ProjectMetaDataDaoFactory;
import org.skeleton.generator.bc.persistence.interfaces.ProjectMetaDataPersistenceHandler;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMetaDataPersistenceHandlerImpl implements ProjectMetaDataPersistenceHandler {

	/*
	 * properties : injected by spring
	 */
	@Autowired
	ProjectMetaDataDaoFactory projectMetaDataDaoFactory;
	
	
	@Override
	public ProjectMetaData loadProjectMetaData(String workspacePath) {

		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		Path path = Paths.get(sourcePath);
		
		if (!Files.exists(path)) {
			throw new ProjectNotFoundException("No data-model folder has been found at the specified path");
		}
		
		return projectMetaDataDaoFactory.getProjectMetaDataDao(workspacePath).loadProjectMetaData(workspacePath);
		
	}

	@Override
	public void persistProjectMetaData(ProjectMetaData projectMetaData) {
		// TODO Auto-generated method stub

	}

}
