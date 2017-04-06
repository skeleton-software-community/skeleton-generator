package org.sklsft.generator.skeletons.core.commands.util.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class UtilPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public UtilPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-util", "pom", FileType.XML, project);
	}

}
