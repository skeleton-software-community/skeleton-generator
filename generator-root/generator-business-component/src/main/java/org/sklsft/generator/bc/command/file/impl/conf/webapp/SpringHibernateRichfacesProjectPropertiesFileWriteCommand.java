package org.sklsft.generator.bc.command.file.impl.conf.webapp;

import java.io.File;

import org.sklsft.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesProjectPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesProjectPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", project.projectName, FileType.PROPERTIES, project);
	}

}
