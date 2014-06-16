package org.skeleton.generator.bc.command.file.impl.conf.context;


import java.io.File;

import org.skeleton.generator.bc.command.file.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;
import org.skeleton.generator.model.om.Project;

public class DataSourceContextFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public DataSourceContextFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + "data-model" + File.separator + "CONTEXT", "datasource-context", FileType.XML, project);
	}

}
