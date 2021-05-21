package org.sklsft.generator.skeletons.core.commands.database.postgresql;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.components.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.IdGeneratorType;
import org.sklsft.generator.skeletons.commands.impl.typed.SqlFileWriteCommand;
import org.sklsft.generator.skeletons.core.database.PostgresqlHandler;

public class PostgresqlTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;

	/*
	 * constructor
	 */
	public PostgresqlTableDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(PostgresqlHandler.NAME) + File.separator + "1" + File.separator + table.myPackage.name.toUpperCase().replace(".", File.separator), table.originalName);

		this.table = table;
	}

	@Override
	public void writeContent() throws IOException {

		createTable();

		if (table.myPackage.model.project.audited) {
			createAuditTable();
		}		

		writeNotOverridableContent();
	}

	/*
	 * create table
	 */
	private void createTable() {
		
		writeLine("-- create table --");
		writeLine("CREATE TABLE " + table.name);
		writeLine("(");
		write("id " + getPostgresqlType(table.idType));

		for (Column column:table.columns) {
			writeLine(",");
			write(column.name + " " + getPostgresqlType(column.dataType));
			if (column.nullable) {
				write(" NULL");
			} else {
				write(" NOT NULL");
			}
		}

		skipLine();
		writeLine(");");
		writeLine("/");
		skipLine();

		writeLine("ALTER TABLE " + table.name + " ADD CONSTRAINT PK_" + table.name + " PRIMARY KEY (ID);");
		writeLine("/");
		skipLine();

		if (table.idGeneratorType.equals(IdGeneratorType.SEQUENCE)) {
			writeLine("-- create sequence --");
			writeLine("CREATE SEQUENCE " + table.sequenceName);
			writeLine("INCREMENT 1");
			writeLine("MINVALUE 0");
			writeLine("MAXVALUE 9223372036854775807");
			writeLine("START 0");
			writeLine("CACHE 1;");
			writeLine("/");
			skipLine();
		}
	}
	

	/*
	 * create audit table
	 */
	private void createAuditTable() {
		writeLine("-- table d'audit des elements --");
		writeLine("CREATE TABLE " + table.name + "_aud");
		writeLine("(");
		writeLine("id integer NOT NULL,");
		writeLine("rev integer NOT NULL,");
		writeLine("revtype smallint NOT NULL,");

		for (Column column:table.columns) {
			writeLine(column.name + " " + getPostgresqlType(column.dataType) + " NULL,");
		}

		writeLine("CONSTRAINT " + table.name + "_aud_pkey PRIMARY KEY (id, rev),");
		writeLine("CONSTRAINT " + table.name + "_aud_rev FOREIGN KEY (rev)");
		writeLine("REFERENCES auditentity (id) MATCH SIMPLE");
		writeLine("ON UPDATE NO ACTION ON DELETE NO ACTION");
		writeLine(")");
		writeLine(";");
		skipLine();
	}
	
	
	public String getPostgresqlType(DataType type) {
		switch (type) {
			case TEXT:
				return "TEXT";
	
			case STRING:
				return "VARCHAR(255)";
				
			case SHORT:
				return "SMALLINT";
				
			case INTEGER:
				return "INTEGER";
	
			case LONG:
				return "BIGINT";
	
			case DOUBLE:
				return "DOUBLE PRECISION";
				
			case DATE:
				return "DATE";
	
			case DATETIME:
				return "TIMESTAMP WITH TIME ZONE";
				
			case BIG_DECIMAL:
				return "NUMERIC";
	
			case BOOLEAN:
				return "BOOLEAN";
	
			default:
				throw new IllegalArgumentException("Unhandled data type " + type);
		}
	}
}
