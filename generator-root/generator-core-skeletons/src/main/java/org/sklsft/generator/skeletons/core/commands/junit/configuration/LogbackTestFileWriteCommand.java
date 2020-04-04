package org.sklsft.generator.skeletons.core.commands.junit.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class LogbackTestFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public LogbackTestFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.testsArtefactName + File.separator + project.model.resourcesFolder, "logback", FileType.XML, project);
	}

}
