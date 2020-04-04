package org.sklsft.generator.skeletons.core.commands.model.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class ModelPomFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public ModelPomFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.modelArtefactName, "pom", FileType.XML, project);
	}

}
