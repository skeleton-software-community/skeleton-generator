package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;

public interface ProjectMetaDataService {

	ProjectMetaData loadProjectMetaData(String folderPath);

	void persistProjectMetaData(ProjectMetaData projectMetaData);

	void initProjectMetaData(ProjectMetaData projectMetaData);
}
