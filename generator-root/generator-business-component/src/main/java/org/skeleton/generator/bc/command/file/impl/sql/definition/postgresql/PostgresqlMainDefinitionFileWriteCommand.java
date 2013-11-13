package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Project;

public class PostgresqlMainDefinitionFileWriteCommand extends SqlFileWriteCommand {

	@SuppressWarnings("unused")
	private Project project;

	public PostgresqlMainDefinitionFileWriteCommand(Project project) {
		super(project.sourceFolder + "\\SQL", "MAIN");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {

        writeLine("DROP SCHEMA IF EXISTS public CASCADE;");
        writeLine("CREATE SCHEMA public;");
	
        writeNotOverridableContent();
	}
}
