package org.sklsft.generator.skeletons.core.commands.bc.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringBusinessComponentFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringBusinessComponentFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-component/src/main/resources", "applicationContext-" + project.projectName + "-business-component", FileType.XML, project);
	}

}
