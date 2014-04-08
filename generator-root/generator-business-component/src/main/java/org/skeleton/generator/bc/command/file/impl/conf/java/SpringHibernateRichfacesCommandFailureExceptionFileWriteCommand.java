package org.skeleton.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesCommandFailureExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesCommandFailureExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.testExceptionPackageName.replace(".", File.separator) + File.separator, "CommandFailureException", FileType.JAVA, project);
	}

}
