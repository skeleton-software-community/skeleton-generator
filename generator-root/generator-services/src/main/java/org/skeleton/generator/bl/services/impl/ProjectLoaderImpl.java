package org.skeleton.generator.bl.services.impl;

import javax.annotation.Resource;

import org.skeleton.generator.bc.factory.model.interfaces.ProjectFactory;
import org.skeleton.generator.bl.services.interfaces.ProjectLoader;
import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProjectLoaderImpl implements ProjectLoader {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectLoader.class);
	
	/*
	 * properties injected by spring
	 */
	@Autowired
	ProjectMetaDataDao projectMetaDataDao;
	@Resource(name="javaProjectFactory")
	ProjectFactory projectFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.bl.services.interfaces.ProjectLoader#loadProject(java.lang.String)
	 */
	@Override
	public Project loadProject(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException {

		logger.info("start reading meta data");
		ProjectMetaData projectMetaData = projectMetaDataDao.loadProjectMetaData(folderPath);
		logger.info("end reading meta data");
		
		logger.info("start building project : " + projectMetaData.getProjectName());
		Project project = projectFactory.buildProject(projectMetaData);
		logger.info("end building project : " + projectMetaData.getProjectName());
		return project;
	}

	
}
