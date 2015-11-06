package org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sklsft.generator.bc.file.command.impl.sql.SqlFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;

public class PostgresqlTableFkDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public PostgresqlTableFkDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + "2" + File.separator + table.myPackage.name.toUpperCase(), table.originalName);

		this.table = table;

		fieldMap = new HashMap<>();

		for (int i = 0; i < table.getInsertColumnList().size(); i++) {
			fieldMap.put(table.getInsertColumnList().get(i).name, "ARG" + i);
		}

		for (int i = 0; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				fieldMap.put(table.columns.get(i).name, "ID_ARG" + i);
			}
		}
	}

	@Override
	public void writeContent() throws IOException {

		createTableFks();

		writeNotOverridableContent();

	}

	/*
	 * create table
	 */
	private void createTableFks() {
		writeLine("-- table foreign keys and indexes --");
		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("ALTER TABLE " + table.name + " ADD CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + this.table.columns.get(i).name + ") REFERENCES " + table.columns.get(i).referenceTable.name);
				if (this.table.columns.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
				writeLine(";");
				writeLine("/");
				skipLine();
			}
		}

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("CREATE INDEX FK_" + table.name + "_" + i + " ON " + this.table.name + "(" + this.table.columns.get(i).name + ");");
				writeLine("/");
				skipLine();
			}
		}
	}

}
