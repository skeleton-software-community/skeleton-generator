package org.sklsft.generator.skeletons.rest.commands.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringRestWebXmlFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringRestWebXmlFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-rest/src/main/webapp/WEB-INF", "web", FileType.XML, project);
	}

}
