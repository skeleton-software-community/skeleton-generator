package org.sklsft.generator.bc.persistence.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.repository.dao.metadata.interfaces.ProjectMetaDataDao;

/**
 * Used to determine which persistence mode is to be used to :
 * <li>load a project depending on which persistence mode is detected
 * <li>persist a project meta data depending on the nature of the project meta data
 * @author Nicolas
 *
 */
public interface ProjectMetaDataDaoFactory {
	
	ProjectMetaDataDao getProjectMetaDataDao(ProjectMetaData projectMetaData);
	
	ProjectMetaDataDao getProjectMetaDataDao(String workspacePath);

}
