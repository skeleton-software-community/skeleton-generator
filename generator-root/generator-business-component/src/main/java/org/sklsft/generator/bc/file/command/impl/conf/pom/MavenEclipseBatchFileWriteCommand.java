package org.sklsft.generator.bc.file.command.impl.conf.pom;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class MavenEclipseBatchFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public MavenEclipseBatchFileWriteCommand(Project project) {
		super(project.workspaceFolder, "maven-eclipse", FileType.BAT, project);
	}

}
