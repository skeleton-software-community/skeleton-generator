package org.skeleton.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesBuildFailureExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesBuildFailureExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.testExceptionPackageName.replace(".", File.separator) + File.separator, "BuildFailureException", FileType.JAVA, project);
	}

}
