package org.sklsft.generator.bc.command.file.impl.conf.pom;

import java.io.File;

import org.sklsft.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesBusinessModelPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesBusinessModelPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model", "pom", FileType.XML, project);
	}

}
