package org.sklsft.generator.bc.file.command.impl.conf.webapp.mvc.filter;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class RichfacesCustomFilterFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public RichfacesCustomFilterFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.mvcFilterPackageName.replace(".", File.separator) + File.separator, "CustomFilter", FileType.JAVA, project);
	}

}
