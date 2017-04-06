package org.sklsft.generator.skeletons.core.commands.api.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class ApiPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ApiPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-api", "pom", FileType.XML, project);
	}

}
