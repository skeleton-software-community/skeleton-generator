package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.metadata.DataType;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

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
		createInsert();
		createUpdate();

		writeNotOverridableContent();

		skipLine();
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

	/*
	 * create insert functions
	 */
	private void createInsert() {
		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- used to insert an element knowing business keys --");
		writeLine("CREATE OR REPLACE FUNCTION insert_" + table.name.toLowerCase() + "_by_code");

		writeLine("(");

		write("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPostgresqlType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPostgresqlType(insertColumnList.get(i).dataType));
		}

		writeLine(")");
		writeLine("RETURNS void AS '");
		writeLine("DECLARE");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + " BIGINT" + ";");
			}
		}

		writeLine("BEGIN");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("select find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();
				boolean begin = true;

				for (int j = 0; j < tempColumnList.size(); j++) {
					if (begin) {
						write("v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name));
						begin = false;
					} else {
						write(", v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name));
					}
				}

				writeLine(") into v" + fieldMap.get(this.table.columns.get(i).name) + ";");

			}
		}

		write("perform insert_" + table.name.toLowerCase() + " (v" + fieldMap.get(this.table.columns.get(1).name));

		for (int i = 2; i < this.table.columns.size(); i++) {
			write(", v" + fieldMap.get(this.table.columns.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("'");
		writeLine("LANGUAGE plpgsql;");
		writeLine("/");
		skipLine();
	}

	/*
	 * create update functions
	 */
	private void createUpdate() {
		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- used to update an element knowing business keys --");
		writeLine("CREATE OR REPLACE FUNCTION update_" + table.name.toLowerCase() + "_by_code");

		writeLine("(");

		write("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPostgresqlType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPostgresqlType(insertColumnList.get(i).dataType));
		}

		writeLine(")");
		writeLine("RETURNS void AS '");
		writeLine("DECLARE");
		
		writeLine("vID BIGINT;");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + " BIGINT" + ";");
			}
		}

		writeLine("BEGIN");
		
		write("select find_" + this.table.name.toLowerCase() + " (");

		tempColumnList = this.table.getFindColumnList();
		boolean begin = true;

		for (int j = 0; j < tempColumnList.size(); j++) {
			if (begin) {
				write("v" + fieldMap.get(tempColumnList.get(j).name));
				begin = false;
			} else {
				write(", v" + fieldMap.get(tempColumnList.get(j).name));
			}
		}

		writeLine(") into vID;");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("select find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();
				begin = true;

				for (int j = 0; j < tempColumnList.size(); j++) {
					if (begin) {
						write("v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name));
						begin = false;
					} else {
						write(", v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name));
					}
				}

				writeLine(") into v" + fieldMap.get(this.table.columns.get(i).name) + ";");

			}
		}

		write("perform update_" + table.name.toLowerCase() + " (vID");

		for (int i = 1; i < this.table.columns.size(); i++) {
			write(", v" + fieldMap.get(this.table.columns.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("'");
		writeLine("LANGUAGE plpgsql;");
		skipLine();
	}

}
