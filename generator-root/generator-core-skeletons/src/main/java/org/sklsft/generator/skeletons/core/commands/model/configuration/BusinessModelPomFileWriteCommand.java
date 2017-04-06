package org.sklsft.generator.skeletons.core.commands.model.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class BusinessModelPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public BusinessModelPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model", "pom", FileType.XML, project);
	}

}
