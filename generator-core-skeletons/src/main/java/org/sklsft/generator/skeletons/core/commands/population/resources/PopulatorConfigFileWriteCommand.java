package org.sklsft.generator.skeletons.core.commands.population.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PopulatorConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PopulatorConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.populatorArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.executorPackageName.replace(".", File.separator) + File.separator, "ApplicationConfig", FileType.JAVA, project);
	}
}