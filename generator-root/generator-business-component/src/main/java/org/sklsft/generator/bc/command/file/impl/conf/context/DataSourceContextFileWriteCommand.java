package org.sklsft.generator.bc.command.file.impl.conf.context;


import java.io.File;

import org.sklsft.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.model.om.Project;

public class DataSourceContextFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public DataSourceContextFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + "data-model" + File.separator + "CONTEXT", "datasource-context", FileType.XML, project);
	}

}
