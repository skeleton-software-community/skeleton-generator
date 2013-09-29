package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.exception.InvalidProjectMetaDataException;
import org.skeleton.generator.exception.ProjectNotFoundException;
import org.skeleton.generator.model.om.Project;

public interface ProjectLoader {

	Project loadProject(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException;
}
