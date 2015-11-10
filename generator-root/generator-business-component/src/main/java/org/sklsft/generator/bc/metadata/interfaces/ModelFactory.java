package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface ModelFactory {

	Model buildModel(ProjectMetaData projectMetaData, Project project);
}
