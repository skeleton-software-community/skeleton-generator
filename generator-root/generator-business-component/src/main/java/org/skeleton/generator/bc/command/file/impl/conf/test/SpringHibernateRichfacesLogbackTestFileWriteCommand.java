package org.skeleton.generator.bc.command.file.impl.conf.test;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesLogbackTestFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesLogbackTestFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/test/resources", "logback-test", FileType.XML, project);
	}

}
