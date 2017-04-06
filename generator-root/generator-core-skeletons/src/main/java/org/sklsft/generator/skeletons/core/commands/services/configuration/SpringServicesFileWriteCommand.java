package org.sklsft.generator.skeletons.core.commands.services.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringServicesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringServicesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/main/resources", "applicationContext-" + project.projectName + "-services", FileType.XML, project);
	}

}
