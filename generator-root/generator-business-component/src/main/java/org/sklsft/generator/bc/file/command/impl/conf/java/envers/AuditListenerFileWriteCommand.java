package org.sklsft.generator.bc.file.command.impl.conf.java.envers;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class AuditListenerFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public AuditListenerFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.enversPackageName.replace(".", File.separator) + File.separator, "AuditListener", FileType.JAVA, project);
	}

}
