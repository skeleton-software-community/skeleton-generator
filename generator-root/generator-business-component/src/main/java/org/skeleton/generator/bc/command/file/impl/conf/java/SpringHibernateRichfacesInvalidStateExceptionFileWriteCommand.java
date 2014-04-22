package org.skeleton.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesInvalidStateExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesInvalidStateExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.serviceExceptionPackageName.replace(".", File.separator) + File.separator, "InvalidStateException", FileType.JAVA, project);
	}

}
