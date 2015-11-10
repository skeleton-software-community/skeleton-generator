package org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.file.command.impl.sql.SqlFileWriteCommand;
import org.sklsft.generator.model.domain.Project;

public class PostgresqlMainDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Project project;

	public PostgresqlMainDefinitionFileWriteCommand(Project project) {
		super(project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER, "MAIN");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {

        writeLine("DROP SCHEMA IF EXISTS public CASCADE;");
        writeLine("CREATE SCHEMA public;");
        
        if (project.audited) {
        	skipLine();
        	createAuditTable();
        	skipLine();
        }
	
        writeNotOverridableContent();
	}

	private void createAuditTable() {
		
		writeLine("CREATE TABLE auditentity");
		writeLine("(");
		writeLine("id BIGINT NOT NULL,");
		writeLine((char)34 + "timestamp" + (char)34 + " BIGINT NOT NULL,");
		writeLine("login VARCHAR(255),");
		writeLine("CONSTRAINT auditentity_pkey PRIMARY KEY (id)");
		writeLine(")");
		writeLine(";");
		
		writeLine("CREATE SEQUENCE hibernate_sequence");
		writeLine("INCREMENT 1");
		writeLine("MINVALUE 1");
		writeLine("MAXVALUE 9223372036854775807");
		writeLine("START 1");
		writeLine("CACHE 1;");
	}
}
