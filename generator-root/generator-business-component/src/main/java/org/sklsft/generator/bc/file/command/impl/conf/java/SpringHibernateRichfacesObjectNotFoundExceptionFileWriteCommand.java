package org.sklsft.generator.bc.file.command.impl.conf.java;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesObjectNotFoundExceptionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesObjectNotFoundExceptionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-api/src/main/java/" + project.model.daoExceptionPackageName.replace(".", File.separator) + File.separator, "ObjectNotFoundException", FileType.JAVA, project);
	}

}
