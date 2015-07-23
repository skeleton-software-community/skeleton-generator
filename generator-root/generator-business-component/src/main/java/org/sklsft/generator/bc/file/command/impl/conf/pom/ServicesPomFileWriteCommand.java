package org.sklsft.generator.bc.file.command.impl.conf.pom;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class ServicesPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ServicesPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services", "pom", FileType.XML, project);
	}

}
