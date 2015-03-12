package org.sklsft.generator.bc.file.command.impl.conf.java.mvc.controller;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesBaseControllerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesBaseControllerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.controllerPackageName.replace(".", File.separator) + File.separator, "BaseController", FileType.JAVA, project);
	}

}
