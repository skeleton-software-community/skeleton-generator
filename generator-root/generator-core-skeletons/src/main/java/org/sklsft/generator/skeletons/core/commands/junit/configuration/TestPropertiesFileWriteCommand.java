package org.sklsft.generator.skeletons.core.commands.junit.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class TestPropertiesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public TestPropertiesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-test/src/test/resources", project.projectName, FileType.PROPERTIES, project);
	}

}
