package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PrimefacesWebappPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PrimefacesWebappPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-webapp", "pom", FileType.XML, project);
	}

}
