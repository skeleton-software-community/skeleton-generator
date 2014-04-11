package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;

/**
 * Defines the contract of a project loader : get a project metadata representation from its persistent representation
 * @author Nicolas Thibault
 *
 */
public interface ProjectLoader {

	Project loadProject(ProjectMetaData projectMetaData);
}
