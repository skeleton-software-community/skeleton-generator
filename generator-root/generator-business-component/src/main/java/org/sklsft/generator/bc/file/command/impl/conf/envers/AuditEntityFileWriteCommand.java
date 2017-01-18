package org.sklsft.generator.bc.file.command.impl.conf.envers;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class AuditEntityFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public AuditEntityFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-model/src/main/java/" + project.model.enversPackageName.replace(".", File.separator) + File.separator, "AuditEntity", FileType.JAVA, project);
	}

}
