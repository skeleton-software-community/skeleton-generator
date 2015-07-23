package org.sklsft.generator.bc.file.command.impl.conf.spring.test;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class LogbackTestFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public LogbackTestFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/test/resources", "logback-test", FileType.XML, project);
	}

}
