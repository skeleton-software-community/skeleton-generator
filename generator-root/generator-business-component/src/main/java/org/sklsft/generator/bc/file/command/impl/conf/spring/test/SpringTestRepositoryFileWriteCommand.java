package org.sklsft.generator.bc.file.command.impl.conf.spring.test;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class SpringTestRepositoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringTestRepositoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-repository/src/test/resources", "applicationContext-" + project.projectName + "-repository-test", FileType.XML, project);
	}

}
