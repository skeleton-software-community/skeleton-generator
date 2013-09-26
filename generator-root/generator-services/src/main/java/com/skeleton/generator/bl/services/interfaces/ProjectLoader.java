package com.skeleton.generator.bl.services.interfaces;

import com.skeleton.generator.exception.InvalidProjectMetaDataException;
import com.skeleton.generator.exception.ProjectNotFoundException;
import com.skeleton.generator.model.om.Project;

public interface ProjectLoader {

	Project loadProject(String folderPath) throws ProjectNotFoundException, InvalidProjectMetaDataException;
}
