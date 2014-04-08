package org.skeleton.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/main/java/" + project.model.commandPackageName.replace(".", File.separator) + File.separator, "CommandBuilderFactory", FileType.JAVA, project);
	}

}
