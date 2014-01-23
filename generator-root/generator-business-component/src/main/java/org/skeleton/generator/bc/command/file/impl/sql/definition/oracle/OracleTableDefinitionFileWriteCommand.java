package org.skeleton.generator.bc.command.file.impl.sql.definition.oracle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.QualifiedColumn;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.util.metadata.DataType;

public class OracleTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private String sequenceName;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public OracleTableDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + "\\SQL\\" + table.myPackage.name.toUpperCase(), table.originalName);

		this.table = table;
		this.sequenceName = table.name + "_id_seq";

		fieldMap = new HashMap<>();

		for (int i = 0; i < table.getInsertColumnList().size(); i++) {
			fieldMap.put(table.getInsertColumnList().get(i).name, "ARG" + i);
		}

		for (int i = 0; i < table.columnList.size(); i++) {
			if (table.columnList.get(i).referenceTable != null) {
				fieldMap.put(table.columnList.get(i).name, "ID_ARG" + i);
			}
		}
	}

	@Override
	public void writeContent() throws IOException {
		
		createGet();
		
		createTable();
		
		if (table.myPackage.model.project.audited) {
			createAuditTable();
		}
		
		createFind();
		createInsert();
		createUpdate();

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
		write(table.columnList.get(0).name + " " + DataType.getOracleType(table.columnList.get(0).dataType));

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
		
		write("CONSTRAINT UC_" + table.name + " UNIQUE (" + this.table.columnList.get(1).name);
		for (int i = 2; i <= this.table.cardinality; i++) {
			write("," + this.table.columnList.get(i).name);
		}
		writeLine(")");
		
		writeLine(", CONSTRAINT PK_" + table.name + " PRIMARY KEY (" + this.table.columnList.get(0).name + ")");
		
		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write(", CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + this.table.columnList.get(i).name + ") REFERENCES " + table.columnList.get(i).referenceTable.name);
				if (this.table.columnList.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
				skipLine();
			}
		}

		writeLine(")");
		writeLine("/");
		skipLine();

		writeLine("-- sequence --");
		writeLine("CREATE SEQUENCE " + sequenceName + " MINVALUE 0 NOMAXVALUE START WITH 0 INCREMENT BY 1 NOCYCLE");
		writeLine("/");
		skipLine();
	}
	
	/*
	 * create audit table
	 */
	private void createAuditTable()
    {
		
		writeLine("-- suppression de la table d'audit --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "_AUD';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
		
        writeLine("-- table d'audit des elements --");
        writeLine("CREATE TABLE " + table.name + "_AUD");
        writeLine("(");
        writeLine(table.columnList.get(0).name + " int NOT NULL,");
        writeLine("REV int NOT NULL,");
        writeLine("REVTYPE smallint NOT NULL,");

        for (int i = 1;i<this.table.columnList.size();i++)
        {
            writeLine(this.table.columnList.get(i).name + " " + DataType.getOracleType(table.columnList.get(i).dataType) + " NULL,");
        }

        writeLine("CONSTRAINT PK_" + table.name + "_AUD PRIMARY KEY (ID, REV),");
        writeLine("CONSTRAINT FK_" + table.name + "_AUD FOREIGN KEY (REV)");
        writeLine("REFERENCES AUDITENTITY (ID)");
        writeLine(")");
        writeLine("/");
        skipLine();
    }
	
	/*
	 * create get stored procedure
	 */
	
	private void createGet() {
		
		writeLine("-- requete permettant de recupperer les elements par business key --");
		
		writeLine("/*SELECT");
		
		List<String> fields = getSelectedFields(table.getQualifiedColumns());
		List<String> jointures = getJointures(table.getQualifiedColumns());
		
		boolean start = true;
		
		for (String field:fields) {
			if (start) {
				start = false;
			} else {
				writeLine(",");
			}
			write(field);
		}
		
		skipLine();
		
		writeLine("FROM " + table.name);
		
		for (String jointure:jointures) {
			writeLine(jointure);
		}
		
		writeLine("*/");
		
	}
	
	private List<String> getSelectedFields(List<QualifiedColumn> qualifiedColumns) {
		List<String> selectedFields = new ArrayList<String>();
		for (QualifiedColumn qualifiedColumn:qualifiedColumns) {
			if (qualifiedColumn.children != null) {
				selectedFields.addAll(getSelectedFields(qualifiedColumn.children));
			} else {
				selectedFields.add(qualifiedColumn.tableAlias + "." + qualifiedColumn.columnName);
			}
		}
		return selectedFields;
	}	

	private List<String> getJointures(List<QualifiedColumn> qualifiedColumns) {
		List<String> jointures = new ArrayList<String>();
		for (QualifiedColumn qualifiedColumn:qualifiedColumns) {
			if (qualifiedColumn.children != null) {
				jointures.add("LEFT OUTER JOIN " + qualifiedColumn.children.get(0).tableName + " " + qualifiedColumn.children.get(0).tableAlias + " ON " + qualifiedColumn.tableAlias + "." + qualifiedColumn.columnName + " = " + qualifiedColumn.children.get(0).tableAlias  + ".ID");
				jointures.addAll(getJointures(qualifiedColumn.children));
			}
		}
		return jointures;
	}
	

	/* 
	 * create find stored procedure
	 */

	private void createFind() {

		List<Column> findColumnList = this.table.getFindColumnList();
		List<Column> tempColumnList;

		writeLine("-- procedure permettant de trouver un element a partir de codes --");
		writeLine("CREATE OR REPLACE PROCEDURE find_" + table.name.toLowerCase());
		writeLine("(");

		for (int i = 0; i < findColumnList.size(); i++) {
			writeLine("v" + fieldMap.get(findColumnList.get(i).name) + " IN " + DataType.getPlOracleType(findColumnList.get(i).dataType) + ",");
		}

		writeLine("vID OUT NUMBER");
		writeLine(") AS");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + " NUMBER;");
			}
		}

		writeLine("BEGIN");
		writeLine("IF v" + fieldMap.get(findColumnList.get(0).name) + " IS NULL");

		for (int i = 1; i < findColumnList.size(); i++) {
			writeLine("AND v" + fieldMap.get(findColumnList.get(i).name) + " IS NULL");
		}
		writeLine("THEN");
		writeLine("vID := NULL;");
		writeLine("ELSE");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write("find_" + this.table.columnList.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columnList.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldMap.get(this.table.columnList.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + ");");
			}
		}

		writeLine("SELECT " + table.name + ".ID");
		writeLine("INTO vID");
		writeLine("FROM " + table.name);
		writeLine("WHERE " + table.name + "." + this.table.columnList.get(1).name + " = v" + fieldMap.get(this.table.columnList.get(1).name));

		for (int i = 2; i <= this.table.cardinality; i++) {
			writeLine("AND " + table.name + "." + this.table.columnList.get(i).name + " = v" + fieldMap.get(this.table.columnList.get(i).name));
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
		writeLine("vREV NUMBER;");
		writeLine("vID NUMBER;");
		writeLine("BEGIN");
		writeLine("vID := " + sequenceName + ".NEXTVAL;");
		write("INSERT INTO " + table.name + " (ID, " + this.table.columnList.get(1).name);

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", " + this.table.columnList.get(i).name);
		}

		write(") VALUES (vID, v" + this.table.columnList.get(1).name);

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", v" + this.table.columnList.get(i).name);
		}
		writeLine(");");
		
		if (table.myPackage.model.project.audited) {
		
			writeLine("vREV := hibernate_sequence.NEXTVAL;");
			
			writeLine("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, current_millis(), 'sys');");
			
			write("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + this.table.columnList.get(1).name);
	
			for (int i = 2; i < this.table.columnList.size(); i++) {
				write(", " + this.table.columnList.get(i).name);
			}
	
			write(") VALUES (vID, vREV, 0, v" + this.table.columnList.get(1).name);
	
			for (int i = 2; i < this.table.columnList.size(); i++) {
				write(", v" + this.table.columnList.get(i).name);
			}
	
			writeLine(");");
			
		}
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- procedure permettant d'inserer un nouvel element a l'aide des codes --");
		writeLine("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase() + "_bc");
		writeLine("(");
		write("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			writeLine(",");
			write("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + " NUMBER;");
			}
		}
		writeLine("BEGIN");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write("find_" + this.table.columnList.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columnList.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldMap.get(this.table.columnList.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + ");");
			}
		}

		write("ins_" + table.name.toLowerCase() + " (v" + fieldMap.get(this.table.columnList.get(1).name));

		for (int i = 2; i < this.table.columnList.size(); i++) {
			write(", v" + fieldMap.get(this.table.columnList.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("/");
		skipLine();
	}
	
	
	/* create update stored procedures */

	private void createUpdate() {

		List<Column> insertColumnList = this.table.getInsertColumnList();
		List<Column> tempColumnList;

		writeLine("-- procedure permettant de mettre a jour un element --");
		writeLine("CREATE OR REPLACE PROCEDURE upd_" + table.name.toLowerCase());
		writeLine("(");

		write("v" + table.columnList.get(0).name + " IN " + DataType.getPlOracleType(table.columnList.get(0).dataType));

		for (int i = 1; i < table.columnList.size(); i++) {
			writeLine(",");
			write("v" + table.columnList.get(i).name + " IN " + DataType.getPlOracleType(table.columnList.get(i).dataType));
		}

		skipLine();
		writeLine(") AS");
		writeLine("vREV NUMBER;");
		writeLine("BEGIN");
		writeLine("UPDATE " + table.name + " set " + this.table.columnList.get(1).name + " = v" + this.table.columnList.get(1).name);

		for (int i = 2; i < this.table.columnList.size(); i++) {
			writeLine(", " + this.table.columnList.get(i).name + " = v" + this.table.columnList.get(i).name);
		}

		writeLine("where ID = vID;");
		
		if (table.myPackage.model.project.audited) {
			
			writeLine("vREV := hibernate_sequence.NEXTVAL;");
			
			writeLine("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, current_millis(), 'sys');");
			
			write("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + this.table.columnList.get(1).name);
	
			for (int i = 2; i < this.table.columnList.size(); i++) {
				write(", " + this.table.columnList.get(i).name);
			}
	
			write(") VALUES (vID, vREV, 1, v" + this.table.columnList.get(1).name);
	
			for (int i = 2; i < this.table.columnList.size(); i++) {
				write(", v" + this.table.columnList.get(i).name);
			}
	
			writeLine(");");
		}
		
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- procedure permettant de mettre a jour element a l'aide des codes --");
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
		
		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + " NUMBER;");
			}
		}
		writeLine("BEGIN");
		
		write("find_" + this.table.name.toLowerCase() + " (");

		tempColumnList = this.table.getFindColumnList();

		for (int j = 0; j < tempColumnList.size(); j++) {
			write("v" + fieldMap.get(tempColumnList.get(j).name) + ",");
		}

		writeLine("vID);");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			if (this.table.columnList.get(i).referenceTable != null) {
				write("find_" + this.table.columnList.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = this.table.columnList.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					write("v" + fieldMap.get(this.table.columnList.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name) + ",");
				}

				writeLine("v" + fieldMap.get(this.table.columnList.get(i).name) + ");");
			}
		}

		write("upd_" + table.name.toLowerCase() + " (vID");

		for (int i = 1; i < this.table.columnList.size(); i++) {
			write(", v" + fieldMap.get(this.table.columnList.get(i).name));
		}

		writeLine(");");
		writeLine("END;");
		writeLine("/");
		skipLine();
	}

}
