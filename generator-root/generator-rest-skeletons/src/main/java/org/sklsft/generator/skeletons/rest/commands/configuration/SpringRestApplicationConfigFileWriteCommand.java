package org.sklsft.generator.skeletons.rest.commands.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestApplicationConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestApplicationConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.restArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.restControllerPackageName.replace(".", File.separator), "ApplicationConfig", FileType.JAVA, project);
	}
}