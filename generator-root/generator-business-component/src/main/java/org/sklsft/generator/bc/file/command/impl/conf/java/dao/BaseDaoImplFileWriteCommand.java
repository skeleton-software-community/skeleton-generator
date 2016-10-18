package org.sklsft.generator.bc.file.command.impl.conf.java.dao;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class BaseDaoImplFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	/*
	 * constructor
	 */
	public BaseDaoImplFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-repository/src/main/java/"
				+ project.model.daoImplPackageName.replace(".", File.separator) + File.separator, "BaseDaoImpl",
				FileType.JAVA, project);
	}
}
