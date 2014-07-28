package org.sklsft.generator.bc.file.command.impl.conf.pom;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesRootPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringHibernateRichfacesRootPomFileWriteCommand(Project project) {
		super(project.workspaceFolder, "pom", FileType.XML, project);
	}

}
