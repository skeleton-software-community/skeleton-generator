package org.sklsft.generator.bc.file.command.impl.conf.java.mvc.controller;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class RichfacesBaseControllerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public RichfacesBaseControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.controllerPackageName.replace(".", File.separator) + File.separator, "BaseController", FileType.JAVA, project);
	}

}
