package org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class Richfaces4CustomFilterFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces4CustomFilterFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.mvcFilterPackageName.replace(".", File.separator) + File.separator, "CustomFilter", FileType.JAVA, project);
	}

}
