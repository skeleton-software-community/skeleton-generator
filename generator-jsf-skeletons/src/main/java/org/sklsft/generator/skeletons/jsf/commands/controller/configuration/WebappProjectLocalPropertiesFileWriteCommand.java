package org.sklsft.generator.skeletons.jsf.commands.controller.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class WebappProjectLocalPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public WebappProjectLocalPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.webappArtefactName + File.separator + project.model.resourcesFolder, "application-local", FileType.PROPERTIES, project);
	}

}
