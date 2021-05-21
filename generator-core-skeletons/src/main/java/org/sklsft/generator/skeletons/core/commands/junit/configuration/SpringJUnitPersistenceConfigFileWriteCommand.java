package org.sklsft.generator.skeletons.core.commands.junit.configuration;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SpringJUnitPersistenceConfigFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SpringJUnitPersistenceConfigFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.persistenceArtefactName + File.separator + project.model.javaSourcesFolder + File.separator + project.model.persistencePackageName.replace(".", File.separator), "JUnitPersistenceConfig", FileType.JAVA, project);
	}
}