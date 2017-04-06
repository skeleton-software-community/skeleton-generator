package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.model.metadata.ProjectMetaData;

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
	 * persists a @{link ProjectMetaData} to an xml file
	 * @param projectMetaData
	 */
	void persistProjectMetaData(ProjectMetaData projectMetaData);

	
	/**
	 * to initialize a project from a @{link ProjectMetaData}
	 * <li>Checks that no project exists in the given folder
	 * <li>Create the necessary folders, the skeleton.xsd and skeleton.xml files
	 * @param projectMetaData
	 */
	void initProjectMetaData(ProjectMetaData projectMetaData);
}
