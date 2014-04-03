package org.skeleton.generator.bc.command.file.impl.conf.webapp;

import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesErrorFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesErrorFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/webapp", "error", FileType.XHTML, project);
	}

}
