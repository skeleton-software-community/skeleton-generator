package org.sklsft.generator.bc.file.command.impl.conf.webapp;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class Richfaces4WebXmlFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public Richfaces4WebXmlFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/webapp/WEB-INF", "web", FileType.XML, project);
	}

}
