package org.sklsft.generator.skeletons.core.commands.population.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PopulatorProjectPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorProjectPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/resources", project.projectName, FileType.PROPERTIES, project);
	}

}
