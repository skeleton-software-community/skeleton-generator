package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.ProjectMetaData;


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
