package org.sklsft.generator.skeletons.core.commands.database.configuration.postgresql;

import java.io.File;

import org.sklsft.generator.components.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.skeletons.core.database.PostgresqlHandler;

public class PostgresqlMainDefinitionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public PostgresqlMainDefinitionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(PostgresqlHandler.NAME), "MAIN", FileType.SQL, project);
	}
}
