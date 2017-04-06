package org.sklsft.generator.skeletons.jsf.commands.controller.configuration;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class MavenInstallBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenInstallBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-install", FileType.BAT, project);
	}

}
