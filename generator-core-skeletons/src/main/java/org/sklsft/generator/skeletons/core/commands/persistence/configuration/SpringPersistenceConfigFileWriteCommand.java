package org.sklsft.generator.skeletons.core.commands.persistence.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringPersistenceConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringPersistenceConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.persistenceArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.persistencePackageName.replace(".", File.separator), "PersistenceConfig", FileType.JAVA, project);
	}
}