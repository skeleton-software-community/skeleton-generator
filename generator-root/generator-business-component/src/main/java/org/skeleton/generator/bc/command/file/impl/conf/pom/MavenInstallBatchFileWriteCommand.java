package org.skeleton.generator.bc.command.file.impl.conf.pom;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class MavenInstallBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenInstallBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-install", FileType.BAT, project);
	}

}
