package com.skeleton.generator.bl.services.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.model.interfaces.ProjectFactory;
import com.skeleton.generator.bl.services.interfaces.ProjectLoader;
import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.exception.ProjectNotFoundException;
import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.model.om.Project;
import com.skeleton.generator.repository.dao.interfaces.ProjectMetaDataDao;

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
