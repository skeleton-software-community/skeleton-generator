package org.sklsft.generator.bc.metadata.impl;

import javax.annotation.Resource;

import org.sklsft.generator.bc.metadata.interfaces.ModelFactory;
import org.sklsft.generator.bc.metadata.interfaces.ProjectFactory;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
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
		project.skeleton = projectMetaData.getSkeleton();
		project.databaseEngine = projectMetaData.getDatabaseEngine();
		project.databaseName = projectMetaData.getDatabaseName();
		project.databaseDNS = projectMetaData.getDatabaseDNS();
		project.databasePort = projectMetaData.getDatabasePort();
		project.databaseUserName = projectMetaData.getDatabaseUserName();
		project.databasePassword = projectMetaData.getDatabasePassword();
		project.audited = projectMetaData.getAudited();
		
		logger.info("start building model");
		project.model = modelFactory.buildModel(projectMetaData, project);
		logger.info("end building model");
		
		return project;
	}

}
