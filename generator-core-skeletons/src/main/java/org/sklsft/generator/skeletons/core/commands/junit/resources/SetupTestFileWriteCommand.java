package org.sklsft.generator.skeletons.core.commands.junit.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class SetupTestFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public SetupTestFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.model.testsArtefactName + File.separator + project.model.testJavaSourcesFolder + File.separator + project.model.junitPackageName.replace(".", File.separator), "SetupTest", FileType.JAVA, project);
	}
}