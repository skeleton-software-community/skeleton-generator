package org.skeleton.generator.bc.command.file.impl.conf.pom;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.util.metadata.FileType;

public class SpringHibernateRichfacesRootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesRootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
