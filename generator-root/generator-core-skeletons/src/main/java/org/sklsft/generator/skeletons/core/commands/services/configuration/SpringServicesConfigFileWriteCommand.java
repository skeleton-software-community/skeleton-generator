package org.sklsft.generator.skeletons.core.commands.services.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringServicesConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringServicesConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.servicesArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.servicesPackageName.replace(".", File.separator), "ServicesConfig", FileType.JAVA, project);
	}
}