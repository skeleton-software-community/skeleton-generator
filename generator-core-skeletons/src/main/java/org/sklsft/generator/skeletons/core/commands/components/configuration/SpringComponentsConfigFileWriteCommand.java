package org.sklsft.generator.skeletons.core.commands.components.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringComponentsConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringComponentsConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.componentsArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.componentsPackageName.replace(".", File.separator), "ComponentsConfig", FileType.JAVA, project);
	}
}