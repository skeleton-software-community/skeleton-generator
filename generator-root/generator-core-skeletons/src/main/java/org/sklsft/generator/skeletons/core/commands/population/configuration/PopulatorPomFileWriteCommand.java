package org.sklsft.generator.skeletons.core.commands.population.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PopulatorPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator", "pom", FileType.XML, project);
	}

}
