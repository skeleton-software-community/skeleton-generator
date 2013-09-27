package com.skeleton.generator.bl.factory.model.interfaces;

import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.model.om.Model;
import com.skeleton.generator.model.om.Project;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface ModelFactory {

	Model buildModel(ProjectMetaData projectMetaData, Project project);
}
