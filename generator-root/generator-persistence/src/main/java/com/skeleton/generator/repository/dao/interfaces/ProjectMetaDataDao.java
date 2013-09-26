package com.skeleton.generator.repository.dao.interfaces;

import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.exception.ProjectNotFoundException;
import com.skeleton.generator.model.metadata.ProjectMetaData;

public interface ProjectMetaDataDao {

	ProjectMetaData loadProjectMetaData(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException;
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
