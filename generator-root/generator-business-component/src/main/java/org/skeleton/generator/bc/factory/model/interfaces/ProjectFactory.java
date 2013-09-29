package org.skeleton.generator.bc.factory.model.interfaces;

import org.skeleton.generator.model.metadata.ProjectMetaData;
import org.skeleton.generator.model.om.Project;


/**
 * General factory interface to build a full project given its MetaData representation
 * @author Nicolas Thibault
 *
 */
public interface ProjectFactory {
	
	/**
	 * builds a full project definition given its MetaData<br/>
	 * @Throws {@link IllegalArgumentException} if convertions from String to enum fails
	 * @param projectMetaData
	 * @return
	 */
	Project buildProject(ProjectMetaData projectMetaData);

}
