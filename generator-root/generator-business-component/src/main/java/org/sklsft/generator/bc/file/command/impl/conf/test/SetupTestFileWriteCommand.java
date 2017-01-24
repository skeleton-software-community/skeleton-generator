package org.sklsft.generator.bc.file.command.impl.conf.test;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class SetupTestFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SetupTestFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-test/src/test/java/" + project.model.junitPackageName.replace(".", File.separator), "SetupTest", FileType.JAVA, project);
	}

}
