package org.sklsft.generator.skeletons.core.commands.population.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PopulatorPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.populatorArtefactName + File.separator + project.model.resourcesFolder, "application", FileType.PROPERTIES, project);
	}
}