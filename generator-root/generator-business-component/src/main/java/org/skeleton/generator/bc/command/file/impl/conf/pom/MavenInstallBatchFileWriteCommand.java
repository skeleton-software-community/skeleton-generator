package org.skeleton.generator.bc.command.file.impl.conf.pom;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class MavenInstallBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenInstallBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-install", FileType.BAT, project);
	}

}
