package org.sklsft.generator.skeletons.core.commands.database.configuration.oracle;


import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class OracleDataSourceContextFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public OracleDataSourceContextFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + "data-model" + File.separator + "CONTEXT", "datasource-context", FileType.XML, project);
	}

}
