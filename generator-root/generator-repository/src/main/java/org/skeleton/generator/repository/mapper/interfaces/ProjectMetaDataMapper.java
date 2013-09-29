package org.skeleton.generator.repository.mapper.interfaces;

import java.util.List;

import org.skeleton.generator.model.metadata.ProjectMetaData;


public interface ProjectMetaDataMapper {

	ProjectMetaData mapProjectMetaData (List<String[]> tokensList, ProjectMetaData projectMetaData);
}
