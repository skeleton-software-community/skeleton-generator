package org.sklsft.generator.skeletons.core.commands.services.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class ServicesPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ServicesPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.servicesArtefactName, "pom", FileType.XML, project);
	}
}