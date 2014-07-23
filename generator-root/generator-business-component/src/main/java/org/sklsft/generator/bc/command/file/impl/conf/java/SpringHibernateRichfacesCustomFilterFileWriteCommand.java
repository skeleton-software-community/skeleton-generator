package org.sklsft.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.sklsft.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesCustomFilterFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesCustomFilterFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/java/" + project.model.filterPackageName.replace(".", File.separator) + File.separator, "CustomFilter", FileType.JAVA, project);
	}

}
