package org.sklsft.generator.skeletons.rest.commands.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestClientSpringContextFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestClientSpringContextFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-rest-client/src/main/resources", "applicationContext-" + project.projectName + "-rest-client", FileType.XML, project);
	}

}