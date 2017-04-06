package org.sklsft.generator.skeletons.jsf.commands.controller.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class ProjectPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ProjectPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", project.projectName, FileType.PROPERTIES, project);
	}

}
