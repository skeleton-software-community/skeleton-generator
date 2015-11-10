package org.sklsft.generator.bc.file.command.impl.conf.context;


import java.io.File;

import org.sklsft.generator.bc.file.command.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;

public class DataSourceContextFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public DataSourceContextFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + "data-model" + File.separator + "CONTEXT", "datasource-context", FileType.XML, project);
	}

}
