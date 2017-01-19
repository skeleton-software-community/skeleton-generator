package org.sklsft.generator.bc.file.command.impl.conf.pom;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class TestPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public TestPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-test", "pom", FileType.XML, project);
	}

}
