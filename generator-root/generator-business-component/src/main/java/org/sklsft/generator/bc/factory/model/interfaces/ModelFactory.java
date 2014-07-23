package org.sklsft.generator.bc.factory.model.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Project;


/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface ModelFactory {

	Model buildModel(ProjectMetaData projectMetaData, Project project);
}
