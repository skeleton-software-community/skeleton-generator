package org.sklsft.generator.skeletons.core.commands.database.configuration.postgresql;

import java.io.File;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;

public class PostgresqlMainDefinitionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PostgresqlMainDefinitionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + "data-model" + File.separator + "SQL" + File.separator + "BUILD", "MAIN", FileType.SQL, project);
	}
}
