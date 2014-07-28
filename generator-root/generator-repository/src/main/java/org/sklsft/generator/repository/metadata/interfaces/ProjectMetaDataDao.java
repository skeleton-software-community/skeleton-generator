package org.sklsft.generator.repository.metadata.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;

/**
 * provides methods to :
 * <li>read meta data from its persistent form
 * <li>initialize meta data (puts a source "data-model" folder in the workspace folder)
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
	
	void initProject(ProjectMetaData projectMetaData);
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
}
