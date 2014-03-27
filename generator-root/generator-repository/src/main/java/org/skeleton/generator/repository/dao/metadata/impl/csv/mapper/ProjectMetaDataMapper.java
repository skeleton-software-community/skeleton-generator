package org.skeleton.generator.repository.dao.metadata.impl.csv.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.springframework.stereotype.Component;


@Deprecated
@Component
public class ProjectMetaDataMapper {

	public ProjectMetaData mapProjectMetaData(List<String[]> tokensList, ProjectMetaData projectMetaData) {

		Map<String, String> elements = new HashMap<String, String>();
		
		for (String[] tokens:tokensList) {
			elements.put(tokens[0], tokens[1]);
		}
		
		projectMetaData.setDomainName(elements.get("Domain Name"));
		projectMetaData.setProjectName(elements.get("Project Name"));
		projectMetaData.setSkeleton(elements.get("Skeleton"));
		projectMetaData.setDatabaseDNS(elements.get("Database DNS"));
		projectMetaData.setDatabasePort(elements.get("Database Port"));
		projectMetaData.setDatabaseEngine(elements.get("Database Engine"));
		projectMetaData.setDatabaseName(elements.get("Database Name"));
		projectMetaData.setDatabaseUserName(elements.get("Database User Name"));
		projectMetaData.setDatabasePassword(elements.get("Database Password"));
		projectMetaData.setAudited(Boolean.valueOf(elements.get("Audited")));
		
		return projectMetaData;
	}

}
