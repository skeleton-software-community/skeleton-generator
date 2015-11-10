package org.sklsft.generator.bc.file.command.impl.conf.pom;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class MavenEclipseBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenEclipseBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-eclipse", FileType.BAT, project);
	}

}
