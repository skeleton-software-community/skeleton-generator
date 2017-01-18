package org.sklsft.generator.bc.file.command.impl.conf.population;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class PopulatorProjectPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorProjectPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/resources", project.projectName, FileType.PROPERTIES, project);
	}

}
