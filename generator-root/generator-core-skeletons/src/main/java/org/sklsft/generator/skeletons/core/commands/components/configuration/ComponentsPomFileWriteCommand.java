package org.sklsft.generator.skeletons.core.commands.components.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class ComponentsPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ComponentsPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.componentsArtefactName, "pom", FileType.XML, project);
	}
}