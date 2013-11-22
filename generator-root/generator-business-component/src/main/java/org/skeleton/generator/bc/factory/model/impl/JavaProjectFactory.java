package org.skeleton.generator.bc.factory.model.impl;

import javax.annotation.Resource;

import org.skeleton.generator.bc.factory.model.interfaces.ModelFactory;
import org.skeleton.generator.bc.factory.model.interfaces.ProjectFactory;
import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.DatabaseEngine;
import org.skeleton.generator.util.metadata.SkeletonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




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
		if (projectMetaData.getAudited().equalsIgnoreCase("true")) {
			project.audited = true;
		} else {
			project.audited = false;
		}
		
		logger.info("start building model");
		project.model = modelFactory.buildModel(projectMetaData, project);
		logger.info("end building model");
		
		return project;
	}

}
