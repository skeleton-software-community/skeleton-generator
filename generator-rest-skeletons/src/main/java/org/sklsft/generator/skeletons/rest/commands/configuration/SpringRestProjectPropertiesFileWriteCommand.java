package org.sklsft.generator.skeletons.rest.commands.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestProjectPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestProjectPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.restArtefactName + File.separator + project.model.resourcesFolder, "application", FileType.PROPERTIES, project);
	}

}
