package org.sklsft.generator.bc.file.command.impl.sql.definition.oracle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sklsft.generator.bc.file.command.impl.sql.SqlFileWriteCommand;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.DataType;

public class OracleTableFkDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public OracleTableFkDefinitionFileWriteCommand(Table table) {

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

	private void createTableFks() {

		writeLine("-- table foreign keys and indexes --");
		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("ALTER TABLE " + table.name + " ADD CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + this.table.columns.get(i).name + ") REFERENCES " + table.columns.get(i).referenceTable.name);
				if (this.table.columns.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
				skipLine();
				writeLine("/");
                skipLine();
			}
		}
		
		for (int i = 1; i < this.table.columns.size(); i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                writeLine("CREATE INDEX FK_" + table.name + "_" + i + " ON " + this.table.name + "(" + this.table.columns.get(i).name + ") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND");
                writeLine("/");
                skipLine();
            }
        }
	}


	/*
	 * create insert stored procedures
	 */
	private void createInsert() {
		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- used to insert an element knowing business keys --");
		writeLine("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase() + "_bc");
		writeLine("(");
		write("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + " NUMBER;");
			}
		}
		writeLine("BEGIN");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + ");");
			}
		}

		write("ins_" + table.name.toLowerCase() + " (v" + fieldMap.get(this.table.columns.get(1).name));

		for (int i = 2; i < this.table.columns.size(); i++) {
			write(", v" + fieldMap.get(this.table.columns.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("/");
		skipLine();
	}
	
	
	/*
	 * create update stored procedures
	 */
	private void createUpdate() {

		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- used to update an element knowing business keys --");
		writeLine("CREATE OR REPLACE PROCEDURE upd_" + table.name.toLowerCase() + "_bc");
		writeLine("(");
		
		write("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");

		writeLine("vID NUMBER;");
		
		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + " NUMBER;");
			}
		}
		writeLine("BEGIN");
		
		write("find_" + this.table.name.toLowerCase() + " (");

		tempColumnList = this.table.getFindColumnList();

		for (int j = 0; j < tempColumnList.size(); j++) {
			write("v" + fieldMap.get(tempColumnList.get(j).name) + ",");
		}

		writeLine("vID);");

		for (int i = 1; i < this.table.columns.size(); i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldMap.get(this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + ");");
			}
		}

		write("upd_" + table.name.toLowerCase() + " (vID");

		for (int i = 1; i < this.table.columns.size(); i++) {
			write(", v" + fieldMap.get(this.table.columns.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		skipLine();
	}

}
