package org.sklsft.generator.bc.file.command.impl.conf.java.population;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class PopulatorFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/java/" + project.model.commandExecutorPackageName.replace(".", File.separator) + File.separator, "Populator", FileType.JAVA, project);
	}

}
