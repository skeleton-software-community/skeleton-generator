package org.sklsft.generator.skeletons.jsf.commands.controller.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class LogbackFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public LogbackFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", "logback", FileType.XML, project);
	}

}
