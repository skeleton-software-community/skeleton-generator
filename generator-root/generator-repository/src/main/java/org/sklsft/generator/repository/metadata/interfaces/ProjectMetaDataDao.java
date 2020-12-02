package org.sklsft.generator.repository.metadata.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;

/**
 * provides methods to :
 * <li>read meta data from its persistent form
 * <li>initialize meta data (puts a source "data-model" folder in the workspace folder)
 * <li>persist meta data
 * <li>persist datasource context
 * @author Nicolas Thibault
 *
 */
public interface ProjectMetaDataDao {
	
	public static final String DATA_MODEL_FOLDER_NAME = "data-model";	
	public static final String XML_CONFIG_FILE_NAME = "skeleton.xml";
	public static final String SCHEMA_LOCATION = "skeleton-metadata-3.1.xsd";
	

	ProjectMetaData loadProjectMetaData(String workspacePath);
	
	void initProject(ProjectMetaData projectMetaData);
	
	void persistProjectMetaData(ProjectMetaData projectMetaData);
	
	void persistDatasourceContext(ProjectMetaData projectMetaData);
}
