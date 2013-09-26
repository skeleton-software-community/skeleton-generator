package com.skeleton.generator.repository.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.repository.mapper.interfaces.ProjectMetaDataMapper;


@Component
public class ProjectMetaDataMapperImpl implements ProjectMetaDataMapper {

	@Override
	public ProjectMetaData mapProjectMetaData(List<String[]> tokensList, ProjectMetaData projectMetaData) {

		Map<String, String> elements = new HashMap<String, String>();
		
		for (String[] tokens:tokensList) {
			elements.put(tokens[0], tokens[1]);
		}
		
		projectMetaData.setDomainName(elements.get("Domain Name"));
		projectMetaData.setProjectName(elements.get("Project Name"));
		projectMetaData.setSourceFolder(elements.get("Source Folder"));
		projectMetaData.setWorkspaceFolder(elements.get("Workspace Folder"));
		projectMetaData.setServerDNS(elements.get("Server DNS"));
		projectMetaData.setServerPort(elements.get("Server Port"));
		projectMetaData.setWsUrl(elements.get("Web Service URL"));
		projectMetaData.setSkeleton(elements.get("Skeleton"));
		projectMetaData.setDatabaseEngine(elements.get("Database Engine"));
		projectMetaData.setDatabaseName(elements.get("Database Name"));
		projectMetaData.setUserName(elements.get("User Name"));
		projectMetaData.setPassword(elements.get("Password"));
		
		return projectMetaData;
	}

}
