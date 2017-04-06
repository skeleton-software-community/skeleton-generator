package org.sklsft.generator.skeletons.core.commands.dao.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class RepositoryPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public RepositoryPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-repository", "pom", FileType.XML, project);
	}

}
