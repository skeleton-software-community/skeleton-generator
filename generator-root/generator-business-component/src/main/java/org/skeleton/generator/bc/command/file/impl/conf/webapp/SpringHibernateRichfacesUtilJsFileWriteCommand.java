package org.skeleton.generator.bc.command.file.impl.conf.webapp;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesUtilJsFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesUtilJsFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/webapp/resources/js", "util", FileType.JS, project);
	}

}
