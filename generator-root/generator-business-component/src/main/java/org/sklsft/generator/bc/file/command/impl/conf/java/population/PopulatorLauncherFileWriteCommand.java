package org.sklsft.generator.bc.file.command.impl.conf.java.population;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class PopulatorLauncherFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorLauncherFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/java/" + project.model.commandExecutorPackageName.replace(".", File.separator) + File.separator, "PopulatorLauncher", FileType.JAVA, project);
	}

}
