package org.sklsft.generator.bc.file.command.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sklsft.generator.bc.file.command.impl.sql.SqlFileWriteCommand;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.QualifiedColumn;
import org.sklsft.generator.model.om.Table;

public class PostgresqlStoredProceduresBcFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public PostgresqlStoredProceduresBcFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + "3" + File.separator + table.myPackage.name.toUpperCase(), table.originalName);

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

		createInsertBc();
		createUpdateBc();

		writeNotOverridableContent();
	}
	
	
	/*
	 * create insert functions
	 */
	private void createInsertBc() {
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
	private void createUpdateBc() {
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
