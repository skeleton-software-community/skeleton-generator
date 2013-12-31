package org.skeleton.generator.bc.persistence.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import org.skeleton.generator.bc.persistence.interfaces.ProjectMetaDataDaoFactory;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.exception.UnhandledPersistenceModeException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.skeleton.generator.util.metadata.PersistenceMode;
import org.springframework.stereotype.Component;

@Component
public class ProjectMetaDataDaoFactoryImpl implements ProjectMetaDataDaoFactory {

	/*
	 * properties : injected by spring
	 */
	@Resource(name="projectMetaDataCsvDao")
	private ProjectMetaDataDao projectMetaDataCsvDao;
	
	@Resource(name="projectMetaDataXmlDao")
	private ProjectMetaDataDao projectMetaDataXmlDao;
	
	
	@Override
	public ProjectMetaDataDao getProjectMetaDataDao(ProjectMetaData projectMetaData) {
		
		return getProjectMetaDataDao(projectMetaData.getPersistenceMode());
	}
	

	@Override
	public ProjectMetaDataDao getProjectMetaDataDao(String workspacePath) {
		
		String sourcePath = workspacePath + File.separator + ProjectMetaDataDao.DATA_MODEL_FOLDER_NAME;
		
		Path xmlPath = Paths.get(sourcePath + File.separator + ProjectMetaDataDao.XML_CONFIG_FILE_NAME);
		if (Files.exists(xmlPath)) {
			return getProjectMetaDataDao(PersistenceMode.XML);
		}
		
		Path csvPath = Paths.get(sourcePath + File.separator + ProjectMetaDataDao.CSV_PARAMETERS_FILE_NAME);
		if (Files.exists(csvPath)) {
			return getProjectMetaDataDao(PersistenceMode.CSV);
		}
		
		throw new ProjectNotFoundException("Unable to find a project at the specified path");
	}
	
	
	private ProjectMetaDataDao getProjectMetaDataDao(PersistenceMode persistenceMode) {
		switch (persistenceMode) {
			case CSV:
				return projectMetaDataCsvDao;
				
			case XML:
				return projectMetaDataXmlDao;
				
			default:
				throw new UnhandledPersistenceModeException("No dao has been configured for this persistence mode");
		}
	}

}
