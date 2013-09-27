package com.skeleton.generator.bl.factory.model.interfaces;

import com.skeleton.generator.model.metadata.ProjectMetaData;
import com.skeleton.generator.model.om.Project;


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
