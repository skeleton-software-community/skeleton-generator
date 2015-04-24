package org.sklsft.generator.bc.file.command.impl.conf.java.population;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class CommandFailureExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public CommandFailureExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-populator/src/main/java/" + project.model.populationExceptionPackageName.replace(".", File.separator) + File.separator, "CommandFailureException", FileType.JAVA, project);
	}

}
