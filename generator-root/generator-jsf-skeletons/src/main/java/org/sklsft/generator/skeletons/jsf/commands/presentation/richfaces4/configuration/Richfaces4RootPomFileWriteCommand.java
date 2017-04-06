package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.configuration;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class Richfaces4RootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces4RootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
