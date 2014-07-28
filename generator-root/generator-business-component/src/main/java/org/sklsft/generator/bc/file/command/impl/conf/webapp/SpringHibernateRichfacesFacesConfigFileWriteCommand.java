package org.sklsft.generator.bc.file.command.impl.conf.webapp;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesFacesConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesFacesConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp/src/main/webapp/WEB-INF", "faces-config", FileType.XML, project);
	}

}
