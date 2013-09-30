package org.skeleton.generator.bc.command.file.impl.sql.definition.oracle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.util.metadata.DataType;

public class OracleTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private String sequenceName;
	private Map<String, String> fieldDictionary;

	/*
	 * constructor
	 */
	public OracleTableDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + "\\SQL\\" + table.myPackage.name.toUpperCase(), table.originalName);

		this.table = table;
		this.sequenceName = table.name + "_id_seq";

		fieldDictionary = new HashMap<>();

		for (int i = 0; i < table.getInsertColumnList().size(); i++) {
			fieldDictionary.put(table.getInsertColumnList().get(i).name, "ARG" + i);
		}

		for (int i = 0; i < table.columnList.size(); i++) {
			if (table.columnList.get(i).referenceTable != null) {
				fieldDictionary.put(table.columnList.get(i).name, "ID_ARG" + i);
			}
		}
	}

	@Override
	public void writeContent() throws IOException {
		createTable();
		createFind();
		createInsert();

		writeNotOverridableContent();

		skipLine();
	}

	private void createTable() {
		writeLine("-- suppression de la table --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- suppression de la sequence --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP SEQUENCE " + sequenceName + "';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- table --");
		writeLine("CREATE TABLE " + table.name);
		writeLine("(");
		writeLine(table.columnList.get(0).name + " " + DataType.getOracleType(table.columnList.get(0).dataType) + " PRIMARY KEY");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			writeLine(",");
			write(this.table.columnList.get(i).name + " " + DataType.getOracleType(table.columnList.get(i).dataType));
			if (this.table.columnList.get(i).nullable) {
				write(" NULL");
			} else {
				write(" NOT NULL");
			}
			if (this.table.columnList.get(i).unique) {
				if (!(i == 1 && table.cardinality == 1)) {
					write(" UNIQUE");
				}
			}
			if (this.table.columnList.get(i).referenceTable != null) {
				write(" REFERENCES " + table.columnList.get(i).referenceTable.name);
				if (this.table.columnList.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
			}
		}

		writeLine(",");
		write("CONSTRAINT " + table.name + "_UC UNIQUE (" + this.table.columnList.get(1).name);

		for (int i = 2; i <= this.table.cardinality; i++) {
			write("," + this.table.columnList.get(i).name);
		}
		writeLine(")");
		writeLine(")");
		writeLine("/");
		skipLine();

		writeLine("-- sequence --");
		writeLine("CREATE SEQUENCE " + sequenceName + " MINVALUE 0 NOMAXVALUE START WITH 0 INCREMENT BY 1 NOCYCLE");
		writeLine("/");
		skipLine();
	}

	/* create find stored procedure */

	private void createFind() {

		List<Column> findColumnList = this.table.getFindColumnList();
		List<Column> tempColumnList;

		writeLine("-- procedure permettant de trouver un element a partir de codes --");
		writeLine("CREATE OR REPLACE PROCEDURE find_" + table.name.toLowerCase());
		writeLine("(");

		for (int i = 0; i < findColumnList.size(); i++) {
			writeLine("v" + fieldDictionary.get(findColumnList.get(i).name) + " IN " + DataType.getPlOracleType(findColumnList.get(i).dataType) + ",");
		}

		writeLine("vID OUT NUMBER");
		writeLine(") AS");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				writeLine("v" + fieldDictionary.get(this.table.columnList.get(i).name) + " NUMBER;");
			}
		}

		writeLine("BEGIN");
		skipLine();

		writeLine("IF v" + fieldDictionary.get(findColumnList.get(0).name) + " IS NULL");

		for (int i = 1; i < findColumnList.size(); i++) {
			writeLine("AND v" + fieldDictionary.get(findColumnList.get(i).name) + " IS NULL");
		}
		writeLine("THEN");
		writeLine("vID := NULL;");
		writeLine("ELSE");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write("find_" + this.table.columnList.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columnList.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldDictionary.get(this.table.columnList.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldDictionary.get(this.table.columnList.get(i).name) + ");");
			}
		}

		writeLine("SELECT " + table.name + ".ID");
		writeLine("INTO vID");
		writeLine("FROM " + table.name);
		writeLine("WHERE " + table.name + "." + this.table.columnList.get(1).name + " = v" + fieldDictionary.get(this.table.columnList.get(1).name));

		for (int i = 2; i <= this.table.cardinality; i++) {
			writeLine("AND " + table.name + "." + this.table.columnList.get(i).name + " = v" + fieldDictionary.get(this.table.columnList.get(i).name));
		}

		writeLine(";");
		writeLine("IF vID IS NULL");
		writeLine("THEN");
		writeLine("vID := -1;");
		writeLine("END IF;");
		writeLine("END IF;");
		writeLine("END;");
		writeLine("/");
		skipLine();
	}

	/* create insert stored procedures */

	private void createInsert() {
		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- procedure permettant d'inserer un nouvel element --");
		writeLine("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase());
		writeLine("(");

		write("v" + table.columnList.get(1).name + " IN " + DataType.getPlOracleType(table.columnList.get(1).dataType));

		for (int i = 2; i < table.columnList.size(); i++) {
			writeLine(",");
			write("v" + table.columnList.get(i).name + " IN " + DataType.getPlOracleType(table.columnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");
		skipLine();

		writeLine("BEGIN");

		write("INSERT INTO " + table.name + " (ID, " + this.table.columnList.get(1).name);

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", " + this.table.columnList.get(i).name);
		}

		write(") VALUES (" + sequenceName + ".NEXTVAL, v" + this.table.columnList.get(1).name);

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", v" + this.table.columnList.get(i).name);
		}

		writeLine(");");
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- procedure permettant d'inserer un nouvel element a l'aide des codes --");
		writeLine("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase() + "_bc");
		writeLine("(");
		write("v" + fieldDictionary.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldDictionary.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				writeLine("v" + fieldDictionary.get(this.table.columnList.get(i).name) + " NUMBER;");
			}
		}
		writeLine("BEGIN");
		skipLine();

		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write("find_" + this.table.columnList.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columnList.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldDictionary.get(this.table.columnList.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldDictionary.get(this.table.columnList.get(i).name) + ");");
			}
		}

		write("ins_" + table.name.toLowerCase() + " (v" + fieldDictionary.get(this.table.columnList.get(1).name));

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", v" + fieldDictionary.get(this.table.columnList.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("/");
		skipLine();
	}

}
