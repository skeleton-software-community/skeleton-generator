package org.sklsft.generator.bc.file.command.impl.conf.webapp;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesLogbackFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesLogbackFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", "logback", FileType.XML, project);
	}

}
