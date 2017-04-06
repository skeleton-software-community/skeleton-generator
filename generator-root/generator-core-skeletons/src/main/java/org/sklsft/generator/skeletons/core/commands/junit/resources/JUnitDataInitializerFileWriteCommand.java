package org.sklsft.generator.skeletons.core.commands.junit.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class JUnitDataInitializerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public JUnitDataInitializerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-test/src/main/java/" + project.model.junitDataPackageName.replace(".", File.separator), "JUnitDataInitializer", FileType.JAVA, project);
	}

}
