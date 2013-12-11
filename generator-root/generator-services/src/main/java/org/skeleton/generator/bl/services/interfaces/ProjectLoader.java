package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;

public interface ProjectLoader {

	Project loadProject(ProjectMetaData projectMetaData);
}
