package org.sklsft.generator.bc.command.file.impl.conf.java;

import java.io.File;

import org.sklsft.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesCommandBuilderFactoryFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-services/src/test/java/" + project.model.commandPackageName.replace(".", File.separator) + File.separator, "CommandBuilderFactory", FileType.JAVA, project);
	}

}
