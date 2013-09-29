package org.skeleton.generator.bc.factory.model.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Model;
import org.skeleton.generator.model.om.Project;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface ModelFactory {

	Model buildModel(ProjectMetaData projectMetaData, Project project);
}
