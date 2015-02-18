package org.sklsft.generator.bc.file.command.impl.conf.java.exceptions;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class InvalidStateExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public InvalidStateExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-api/src/main/java/" + project.model.stateExceptionPackageName.replace(".", File.separator) + File.separator, "InvalidStateException", FileType.JAVA, project);
	}

}
