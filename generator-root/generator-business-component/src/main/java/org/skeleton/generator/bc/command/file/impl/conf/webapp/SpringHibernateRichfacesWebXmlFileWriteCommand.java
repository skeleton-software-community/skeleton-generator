package org.skeleton.generator.bc.command.file.impl.conf.webapp;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesWebXmlFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesWebXmlFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/webapp/WEB-INF", "web", FileType.XML, project);
	}

}