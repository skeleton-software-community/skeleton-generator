package org.sklsft.generator.bc.file.command.impl.conf.spring;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringBusinessComponentFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringBusinessComponentFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-component/src/main/resources", "applicationContext-" + project.projectName + "-business-component", FileType.XML, project);
	}

}
