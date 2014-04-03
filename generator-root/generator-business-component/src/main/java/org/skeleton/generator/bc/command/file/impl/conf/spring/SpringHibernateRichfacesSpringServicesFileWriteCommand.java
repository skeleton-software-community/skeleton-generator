package org.skeleton.generator.bc.command.file.impl.conf.spring;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesSpringServicesFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesSpringServicesFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/resources", "applicationContext-" + project.projectName + "-services", FileType.XML, project);
	}

}
