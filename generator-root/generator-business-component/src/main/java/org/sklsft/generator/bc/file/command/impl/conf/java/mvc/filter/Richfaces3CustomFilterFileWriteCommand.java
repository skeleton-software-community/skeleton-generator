package org.sklsft.generator.bc.file.command.impl.conf.java.mvc.filter;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class Richfaces3CustomFilterFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces3CustomFilterFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.filterPackageName.replace(".", File.separator) + File.separator, "CustomFilter", FileType.JAVA, project);
	}

}
