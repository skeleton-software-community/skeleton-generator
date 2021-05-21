package org.sklsft.generator.skeletons.rest.commands.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestClientConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestClientConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.restClientArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.restClientPackageName.replace(".", File.separator), "RestClientConfig", FileType.JAVA, project);
	}
}