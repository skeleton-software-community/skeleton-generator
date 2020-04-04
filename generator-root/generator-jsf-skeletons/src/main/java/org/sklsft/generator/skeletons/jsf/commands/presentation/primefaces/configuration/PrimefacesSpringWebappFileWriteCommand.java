package org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PrimefacesSpringWebappFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PrimefacesSpringWebappFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.webappArtefactName + File.separator + project.model.resourcesFolder, "applicationContext-" + project.projectName + "-webapp", FileType.XML, project);
	}
}