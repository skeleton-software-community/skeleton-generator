package org.sklsft.generator.skeletons.jsf.commands.controller.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class JsfBaseControllerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public JsfBaseControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.controllerPackageName.replace(".", File.separator) + File.separator, "BaseController", FileType.JAVA, project);
	}

}
