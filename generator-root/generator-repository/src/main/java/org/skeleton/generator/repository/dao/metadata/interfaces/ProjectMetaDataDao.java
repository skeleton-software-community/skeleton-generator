package org.skeleton.generator.repository.dao.metadata.interfaces;

import org.skeleton.generator.exception.ConfigurationReadException;
import org.skeleton.generator.model.metadata.ProjectMetaData;

public interface ProjectMetaDataDao {

	ProjectMetaData loadProjectMetaData(String folderPath) throws ConfigurationReadException;
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
