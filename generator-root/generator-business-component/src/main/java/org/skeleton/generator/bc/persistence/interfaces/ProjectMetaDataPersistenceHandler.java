package org.skeleton.generator.bc.persistence.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;

/**
 * has the same signature as a ProjectMetaDataDao<br/>
 * Its implementation will work as a proxy on a dao which will be determined by a factory, <br/>
 * depending on which persistence mode is detected
 * @author Nicolas Thibault
 *
 */
public interface ProjectMetaDataPersistenceHandler {
	
	ProjectMetaData loadProjectMetaData(String workspacePath);
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
