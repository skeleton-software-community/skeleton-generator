package org.sklsft.generator.skeletons.rest.commands.configuration;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestRootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestRootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
