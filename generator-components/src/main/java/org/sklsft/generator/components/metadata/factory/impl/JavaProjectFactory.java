package org.sklsft.generator.components.metadata.factory.impl;

import org.sklsft.generator.components.metadata.factory.interfaces.ModelFactory;
import org.sklsft.generator.components.metadata.factory.interfaces.ProjectFactory;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Autowired
	@Qualifier("javaModelFactory")
	private ModelFactory modelFactory;
	

	public Project buildProject(ProjectMetaData projectMetaData) {
		
		Project project = new Project();
		
		project.domainName = projectMetaData.getDomainName();
		project.projectName = projectMetaData.getProjectName();
		project.sourceFolder = projectMetaData.getSourceFolder();
		project.workspaceFolder = projectMetaData.getWorkspaceFolder();
		project.skeleton = projectMetaData.getSkeleton();
		project.databaseEngine = projectMetaData.getDatabaseEngine();
		project.audited = projectMetaData.getAudited();
		project.tablesTableSpace = projectMetaData.tablesTableSpace;
		project.indexesTableSpace = projectMetaData.indexesTableSpace;
		project.dataSource = projectMetaData.getDataSource();
		
		logger.trace("start building model");
		project.model = modelFactory.buildModel(projectMetaData, project);
		logger.trace("end building model");
		
		return project;
	}

}
