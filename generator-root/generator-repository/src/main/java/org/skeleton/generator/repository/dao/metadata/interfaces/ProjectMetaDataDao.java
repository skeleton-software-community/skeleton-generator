package org.skeleton.generator.repository.dao.metadata.interfaces;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.metadata.ProjectMetaData;

public interface ProjectMetaDataDao {

	ProjectMetaData loadProjectMetaData(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException;
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
