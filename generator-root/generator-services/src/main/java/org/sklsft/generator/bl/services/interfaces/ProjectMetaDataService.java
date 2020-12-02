package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

/**
 * All the necessary services relative to xml representation of a project
 * @author Nicolas Thibault
 *
 */
public interface ProjectMetaDataService {

	/**
	 * loads a @{link ProjectMetaData} from in its xml file
	 * @param folderPath
	 * @return
	 */
	ProjectMetaData loadProjectMetaData(String folderPath);
	
	
	/**
	 * validates a @{link ProjectMetaData}
	 * @return
	 */
	ProjectValidationReport validate(ProjectMetaData project);
	
	
	
	/**
	 * to initialize a project from a @{link ProjectMetaData} and a @{link {@link DataSourceMetaData}
	 * <li>Checks that no project exists in the given folder
	 * <li>Create the necessary folders, the skeleton.xsd, skeleton.xml and datasource-context.xml files
	 * @param projectMetaData
	 */
	void initProjectMetaData(ProjectMetaData projectMetaData);
}
