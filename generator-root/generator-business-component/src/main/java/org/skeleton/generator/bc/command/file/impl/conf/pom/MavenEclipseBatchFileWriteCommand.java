package org.skeleton.generator.bc.command.file.impl.conf.pom;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class MavenEclipseBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenEclipseBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-eclipse", FileType.BAT, project);
	}

}
