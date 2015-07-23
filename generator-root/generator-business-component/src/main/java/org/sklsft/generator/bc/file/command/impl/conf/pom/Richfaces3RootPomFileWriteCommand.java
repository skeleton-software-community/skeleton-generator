package org.sklsft.generator.bc.file.command.impl.conf.pom;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class Richfaces3RootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces3RootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
