package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.metadata.DataType;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.QualifiedColumn;
import org.skeleton.generator.model.om.Table;

public class PostgresqlTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private String sequenceName;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public PostgresqlTableDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER + File.separator + "1" + File.separator + table.myPackage.name.toUpperCase(), table.originalName);

		this.table = table;
		this.sequenceName = table.name + "_id_seq";

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

	/*
	 * create table
	 */
	private void createTable() {
		
		writeLine("-- create table --");
		writeLine("CREATE TABLE " + table.name);
		writeLine("(");
		write(table.columns.get(0).name + " " + DataType.getPostgresqlType(table.columns.get(0).dataType));

		for (int i = 1; i < this.table.columns.size(); i++) {
			writeLine(",");
			write(this.table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType));
			if (this.table.columns.get(i).nullable) {
				write(" NULL");
			} else {
				write(" NOT NULL");
			}
			if (this.table.columns.get(i).unique) {
				if (!(i == 1 && table.cardinality == 1)) {
					write(" UNIQUE");
				}
			}
		}

		skipLine();
		writeLine(");");
		writeLine("/");
		skipLine();
		
		write("ALTER TABLE " + table.name + " ADD CONSTRAINT UC_" + table.name + " UNIQUE (" + this.table.columns.get(1).name);
		for (int i = 2; i <= this.table.cardinality; i++) {
			write("," + this.table.columns.get(i).name);
		}
		writeLine(");");
		writeLine("/");
		skipLine();

		writeLine("ALTER TABLE " + table.name + " ADD CONSTRAINT PK_" + table.name + " PRIMARY KEY (" + this.table.columns.get(0).name + ");");
		writeLine("/");
		skipLine();			

		writeLine("-- create sequence --");
		writeLine("CREATE SEQUENCE " + sequenceName);
		writeLine("INCREMENT 1");
		writeLine("MINVALUE 0");
		writeLine("MAXVALUE 9223372036854775807");
		writeLine("START 0");
		writeLine("CACHE 1;");
		writeLine("/");
		skipLine();
	}

	/*
	 * create audit table
	 */
	private void createAuditTable() {
		writeLine("-- table d'audit des elements --");
		writeLine("CREATE TABLE " + table.name + "_aud");
		writeLine("(");
		writeLine(table.columns.get(0).name + " integer NOT NULL,");
		writeLine("rev integer NOT NULL,");
		writeLine("revtype smallint NOT NULL,");

		for (int i = 1; i < this.table.columns.size(); i++) {
			writeLine(this.table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType) + " NULL,");
		}

		writeLine("CONSTRAINT " + table.name + "_aud_pkey PRIMARY KEY (id, rev),");
		writeLine("CONSTRAINT " + table.name + "_aud_rev FOREIGN KEY (rev)");
		writeLine("REFERENCES auditentity (id) MATCH SIMPLE");
		writeLine("ON UPDATE NO ACTION ON DELETE NO ACTION");
		writeLine(")");
		writeLine(";");
		skipLine();
	}

	private void createGet() {

		writeLine("-- fetch elements with business keys --");

		writeLine("/*SELECT");

		List<String> fields = getSelectedFields(table.getQualifiedColumns());
		List<String> jointures = getJointures(table.getQualifiedColumns());

		boolean start = true;

		for (String field : fields) {
			if (start) {
				start = false;
			} else {
				writeLine(",");
			}
			write(field);
		}

		skipLine();

		writeLine("FROM " + table.name);

		for (String jointure : jointures) {
			writeLine(jointure);
		}

		writeLine("*/");
		skipLine();

	}

	private List<String> getSelectedFields(List<QualifiedColumn> qualifiedColumns) {
		List<String> selectedFields = new ArrayList<String>();
		for (QualifiedColumn qualifiedColumn : qualifiedColumns) {
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
		for (QualifiedColumn qualifiedColumn : qualifiedColumns) {
			if (qualifiedColumn.children != null) {
				jointures.add("LEFT OUTER JOIN " + qualifiedColumn.children.get(0).tableName + " " + qualifiedColumn.children.get(0).tableAlias + " ON " + qualifiedColumn.tableAlias + "." + qualifiedColumn.columnName + " = " + qualifiedColumn.children.get(0).tableAlias + ".ID");
				jointures.addAll(getJointures(qualifiedColumn.children));
			}
		}
		return jointures;
	}

	/* 
	 * create find functions
	 */
	private void createFind() {

		List<Column> findColumnList = this.table.getFindColumnList();
		List<Column> tempColumnList;

		writeLine("-- used to find element from business key --");
		writeLine("CREATE OR REPLACE FUNCTION find_" + table.name.toLowerCase());
		writeLine("(");

		for (int i = 0; i < findColumnList.size(); i++) {
			writeLine("v" + fieldMap.get(findColumnList.get(i).name) + " " + DataType.getPostgresqlType(findColumnList.get(i).dataType) + ",");

		}

		writeLine("OUT vID BIGINT");

		writeLine(")");

		writeLine("AS '");

		writeLine("DECLARE");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				writeLine("v" + fieldMap.get(this.table.columns.get(i).name) + " BIGINT;");

			}
		}
		writeLine("BEGIN");

		writeLine("IF v" + fieldMap.get(findColumnList.get(0).name) + " IS NULL");

		for (int i = 1; i < findColumnList.size(); i++) {
			writeLine("AND v" + fieldMap.get(findColumnList.get(i).name) + " IS NULL");

		}
		writeLine("THEN");

		writeLine("vID = NULL;");

		writeLine("ELSE");

		for (int i = 1; i <= this.table.cardinality; i++) {
			if (this.table.columns.get(i).referenceTable != null) {
				write("SELECT find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

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

		writeLine("SELECT " + table.name + ".ID");

		writeLine("INTO vID");
		writeLine("FROM " + table.name);
		writeLine("WHERE " + table.name + "." + this.table.columns.get(1).name + " = v" + fieldMap.get(this.table.columns.get(1).name));

		for (int i = 2; i <= this.table.cardinality; i++) {

			writeLine("AND " + table.name + "." + this.table.columns.get(i).name + " = v" + fieldMap.get(this.table.columns.get(i).name));
		}

		writeLine(";");

		writeLine("IF vID IS NULL");
		writeLine("THEN");
		writeLine("vID = -1;");
		writeLine("END IF;");
		writeLine("END IF;");
		writeLine("END;");
		writeLine("'");
		writeLine("LANGUAGE plpgsql;");
		writeLine("/");
		skipLine();
	}

	/*
	 * create insert function
	 */
	private void createInsert() {

		writeLine("-- used to insert a new element --");
		writeLine("CREATE OR REPLACE FUNCTION insert_" + table.name.toLowerCase());

		writeLine("(");

		write("v" + table.columns.get(1).name + " " + DataType.getPostgresqlType(table.columns.get(1).dataType));

		for (int i = 2; i < table.columns.size(); i++) {
			writeLine(",");
			write("v" + table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType));
		}

		writeLine(")");
		writeLine("RETURNS void AS '");
		writeLine("DECLARE");
		writeLine("vREV BIGINT;");
		writeLine("vID BIGINT;");
		writeLine("BEGIN");
		writeLine("select nextval(''" + sequenceName + "'') into vID;");
		write("INSERT INTO " + table.name + " (ID, " + this.table.columns.get(1).name);

		for (int i = 2; i < this.table.columns.size(); i++) {
			write(", " + this.table.columns.get(i).name);
		}

		write(") VALUES (vID, v" + this.table.columns.get(1).name);

		for (int i = 2; i < this.table.columns.size(); i++) {
			write(", v" + this.table.columns.get(i).name);
		}
		skipLine();
		writeLine(");");
		
		if (table.myPackage.model.project.audited) {
			
			writeLine("select nextval(''hibernate_sequence'') into vREV;");
			
			writeLine("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, extract(epoch from CURRENT_TIMESTAMP)*1000, ''sys'');");
			
			write("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + this.table.columns.get(1).name);
	
			for (int i = 2; i < this.table.columns.size(); i++) {
				write(", " + this.table.columns.get(i).name);
			}
	
			write(") VALUES (vID, vREV, 0, v" + this.table.columns.get(1).name);
	
			for (int i = 2; i < this.table.columns.size(); i++) {
				write(", v" + this.table.columns.get(i).name);
			}
	
			writeLine(");");
			
		}
		
		writeLine("END;");
		writeLine("'");
		writeLine("LANGUAGE plpgsql;");
		writeLine("/");
		skipLine();

	}

	/* create update functions */

	private void createUpdate() {

		writeLine("-- used to update an element --");
		writeLine("CREATE OR REPLACE FUNCTION update_" + table.name.toLowerCase());

		write("(");

		write("v" + table.columns.get(0).name + " " + DataType.getPostgresqlType(table.columns.get(0).dataType));

		for (int i = 1; i < table.columns.size(); i++) {
			writeLine(",");
			write("v" + table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType));
		}

		skipLine();
		writeLine(")");
		writeLine("RETURNS void AS '");
		writeLine("DECLARE");
		writeLine("vREV BIGINT;");
		writeLine("BEGIN");

		writeLine("UPDATE " + table.name + " set " + this.table.columns.get(1).name + " = v" + this.table.columns.get(1).name);

		for (int i = 2; i < this.table.columns.size(); i++) {
			writeLine(", " + this.table.columns.get(i).name + " = v" + this.table.columns.get(i).name);
		}

		writeLine("where ID = vID;");
		
		if (table.myPackage.model.project.audited) {
			
			writeLine("select nextval(''hibernate_sequence'') into vREV;");
			
			writeLine("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, extract(epoch from CURRENT_TIMESTAMP)*1000, ''sys'');");
			
			write("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + this.table.columns.get(1).name);
	
			for (int i = 2; i < this.table.columns.size(); i++) {
				write(", " + this.table.columns.get(i).name);
			}
	
			write(") VALUES (vID, vREV, 1, v" + this.table.columns.get(1).name);
	
			for (int i = 2; i < this.table.columns.size(); i++) {
				write(", v" + this.table.columns.get(i).name);
			}
	
			writeLine(");");
		}

		writeLine("END;");

		writeLine("'");
		writeLine("LANGUAGE plpgsql;");
		writeLine("/");
		skipLine();
	}
}
