package org.skeleton.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesCommandExecutorFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesCommandExecutorFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/test/java/" + project.model.commandPackageName.replace(".", File.separator) + File.separator, "CommandExecutor", FileType.JAVA, project);
	}

}
