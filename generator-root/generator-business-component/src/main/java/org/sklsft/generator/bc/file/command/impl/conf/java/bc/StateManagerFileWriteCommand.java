package org.sklsft.generator.bc.file.command.impl.conf.java.bc;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class StateManagerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	/*
	 * constructor
	 */
	public StateManagerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-component/src/main/java/" + project.model.stateManagerPackageName.replace(".", File.separator) + File.separator, "StateManager", FileType.JAVA, project);
	}
}
