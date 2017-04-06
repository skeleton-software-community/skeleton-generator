package org.sklsft.generator.skeletons.core.commands.model.resources;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class AuditListenerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public AuditListenerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.enversPackageName.replace(".", File.separator) + File.separator, "AuditListener", FileType.JAVA, project);
	}

}
