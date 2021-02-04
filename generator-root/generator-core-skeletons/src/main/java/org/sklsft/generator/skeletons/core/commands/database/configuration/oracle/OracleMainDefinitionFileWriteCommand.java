package org.sklsft.generator.skeletons.core.commands.database.configuration.oracle;

import java.io.File;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.templatized.ProjectTemplatizedFileWriteCommand;
import org.sklsft.generator.skeletons.core.database.OracleHandler;

public class OracleMainDefinitionFileWriteCommand extends ProjectTemplatizedFileWriteCommand {

	public OracleMainDefinitionFileWriteCommand(Project project) {
		super(project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(OracleHandler.NAME), "MAIN", FileType.SQL, project);
	}
}
