package org.sklsft.generator.bc.file.command.impl.conf.java.junit;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class CommandBuilderFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public CommandBuilderFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/test/java/" + project.model.commandPackageName.replace(".", File.separator) + File.separator, "CommandBuilder", FileType.JAVA, project);
	}

}
