package org.skeleton.generator.bc.command.file.impl.conf.pom;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class MavenEclipseBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenEclipseBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-eclipse", FileType.BAT, project);
	}

}
