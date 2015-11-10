package org.sklsft.generator.bc.file.command.impl.conf.spring.test;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class SpringTestBusinessComponentFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringTestBusinessComponentFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-component/src/test/resources", "applicationContext-" + project.projectName + "-business-component-test", FileType.XML, project);
	}

}
