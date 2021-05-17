package org.sklsft.generator.skeletons.core.commands.database.postgresql;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Index;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.domain.database.UniqueConstraint;
import org.sklsft.generator.skeletons.commands.impl.typed.SqlFileWriteCommand;
import org.sklsft.generator.skeletons.core.database.PostgresqlHandler;

public class PostgresqlTableFkDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;

	/*
	 * constructor
	 */
	public PostgresqlTableFkDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(PostgresqlHandler.NAME) + File.separator + "2" + File.separator + table.myPackage.name.toUpperCase().replace(".", File.separator), table.originalName);

		this.table = table;

	}

	@Override
	public void writeContent() throws IOException {

		createConstraints();
		
		createTableFks();
		
		createIndexes();

		writeNotOverridableContent();

	}
	
	/**
	 * create constraints
	 */
	private void createConstraints() {
		
		for (UniqueConstraint uniqueConstraint:table.uniqueConstraints) {
			write("ALTER TABLE " + table.name + " ADD CONSTRAINT " + uniqueConstraint.name + " UNIQUE (" + uniqueConstraint.columns.get(0).name);
			for (int i = 1; i < uniqueConstraint.columns.size(); i++) {
				write("," + uniqueConstraint.columns.get(i).name);
			}
			writeLine(");");
			writeLine("/");
			skipLine();
		}
	}

	/**
	 * create fks and indexes
	 */
	private void createTableFks() {
		writeLine("-- table foreign keys and indexes --");
		int i = 0;
		for (Column column:table.columns) {
			if (column.referenceTable != null) {
				write("ALTER TABLE " + table.name + " ADD CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + column.name + ") REFERENCES " + column.referenceTable.name);
				if (this.table.columns.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
				writeLine(";");
				writeLine("/");
				skipLine();
			}
			i++;
		}
	}
	
	/**
	 * create indexes
	 */
	private void createIndexes() {
		for (Index index:table.indexes) {
			write("CREATE INDEX " + index.name + " ON " + this.table.name + "(" + index.columns.get(0).name);
			for (int i = 1; i < index.columns.size(); i++) {
				write("," + index.columns.get(i).name);
			}
			writeLine(");");
			writeLine("/");
			skipLine();
		}
	}
}
