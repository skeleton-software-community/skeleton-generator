package org.sklsft.generator.skeletons.core.commands.population.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PopulatorLauncherFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorLauncherFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/java/" + project.model.executorPackageName.replace(".", File.separator) + File.separator, "PopulatorLauncher", FileType.JAVA, project);
	}

}
