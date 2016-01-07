package org.sklsft.generator.bc.file.command.impl.conf.pom;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class Richfaces4RootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces4RootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
