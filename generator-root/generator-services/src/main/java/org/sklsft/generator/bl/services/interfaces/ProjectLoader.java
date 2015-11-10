package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;

/**
 * Defines the contract of a project loader : get a project metadata representation from its persistent representation
 * @author Nicolas Thibault
 *
 */
public interface ProjectLoader {

	Project loadProject(ProjectMetaData projectMetaData);
}
