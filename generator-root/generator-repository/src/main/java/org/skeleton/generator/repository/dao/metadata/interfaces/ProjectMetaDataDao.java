package org.skeleton.generator.repository.dao.metadata.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;

/**
 * provides two methodes to :
 * <li>read meta data from its persistent form
 * <li>persist meta data
 * @author Nicolas Thibault
 *
 */
public interface ProjectMetaDataDao {
	
	public static final String DATA_MODEL_FOLDER_NAME = "data-model";
	
	public static final String XML_CONFIG_FILE_NAME = "skeleton.xml";
	
	public static final String CSV_MODEL_FOLDER_NAME = "MODEL";
	public static final String CSV_PARAMETERS_FILE_NAME = "PARAMETERS.txt";
	

	ProjectMetaData loadProjectMetaData(String workspacePath);
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
