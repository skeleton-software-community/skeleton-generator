package org.sklsft.generator.bc.file.command.impl.java.bc.processor;

import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class ProcessorFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	/*
	 * constructor
	 */
	public ProcessorFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + project.projectName + "-business-component/src/main/java/"
				+ project.model.processorPackageName.replace(".", File.separator) + File.separator, "Processor",
				FileType.JAVA, project);
	}
}
