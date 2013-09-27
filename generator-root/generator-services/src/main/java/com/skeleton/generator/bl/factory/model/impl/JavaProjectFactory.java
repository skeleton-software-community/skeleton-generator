package com.skeleton.generator.bl.factory.model.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skeleton.generator.bl.factory.model.interfaces.ModelFactory;
import com.skeleton.generator.bl.factory.model.interfaces.ProjectFactory;
import com.skeleton.generator.model.enumeration.DatabaseEngine;
import com.skeleton.generator.model.enumeration.SkeletonType;
import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.model.om.Project;


/**
 * 
 * @author Nicolas Thibault
 *
 */
@Component(value="javaProjectFactory")
public class JavaProjectFactory implements ProjectFactory {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProjectFactory.class);
	
	/*
	 * properties injected by spring
	 */
	@Resource(name="javaModelFactory")
	ModelFactory modelFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.bl.factory.interfaces.ProjectFactory#buildProject(com.skeleton.generator.model.metadata.ProjectMetaData)
	 */
	public Project buildProject(ProjectMetaData projectMetaData) {
		
		Project project = new Project();
		
		project.domainName = projectMetaData.getDomainName();
		project.projectName = projectMetaData.getProjectName();
		project.sourceFolder = projectMetaData.getSourceFolder();
		project.workspaceFolder = projectMetaData.getWorkspaceFolder();
		project.serverDNS = projectMetaData.getServerDNS();
		project.serverPort = projectMetaData.getServerPort();
		project.wsUrl = projectMetaData.getWsUrl();
		project.skeletonType = SkeletonType.byValue(projectMetaData.getSkeleton());
		project.databaseEngine = DatabaseEngine.byValue(projectMetaData.getDatabaseEngine());
		project.databaseName = projectMetaData.getDatabaseName();
		project.userName = projectMetaData.getUserName();
		project.password = projectMetaData.getPassword();
		
		logger.info("start building model");
		project.model = modelFactory.buildModel(projectMetaData, project);
		logger.info("end building model");
		
		return project;
	}

}
