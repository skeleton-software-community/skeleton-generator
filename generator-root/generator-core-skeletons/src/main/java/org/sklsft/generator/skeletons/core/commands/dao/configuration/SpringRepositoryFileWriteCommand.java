package org.sklsft.generator.skeletons.core.commands.dao.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRepositoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRepositoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-repository/src/main/resources", "applicationContext-" + project.projectName + "-repository", FileType.XML, project);
	}

}
