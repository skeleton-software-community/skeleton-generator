package org.sklsft.generator.bl.services.impl;

import org.sklsft.generator.bl.services.interfaces.ProjectLoader;
import org.sklsft.generator.components.metadata.factory.interfaces.ProjectFactory;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("javaProjectFactory")
	private ProjectFactory projectFactory;
	

	@Override
	public Project loadProject(ProjectMetaData projectMetaData) {
		
		logger.info("start building project : " + projectMetaData.getProjectName());
		Project project = projectFactory.buildProject(projectMetaData);
		logger.info("end building project : " + projectMetaData.getProjectName());
		return project;
	}

	
}
