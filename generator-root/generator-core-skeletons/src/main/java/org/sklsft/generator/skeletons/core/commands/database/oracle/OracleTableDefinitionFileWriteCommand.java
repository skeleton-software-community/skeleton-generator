package org.sklsft.generator.skeletons.core.commands.database.oracle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.skeletons.commands.impl.typed.SqlFileWriteCommand;

public class OracleTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private String sequenceName;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public OracleTableDefinitionFileWriteCommand(Table table) {

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
		
		createTable();
		
		if (table.myPackage.model.project.audited) {
			createAuditTable();
		}

		writeNotOverridableContent();

		skipLine();
	}

	private void createTable() {
		writeLine("-- drop table --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();

		

		writeLine("-- create table --");
		writeLine("CREATE TABLE " + table.name);
		writeLine("(");
		write(table.columns.get(0).name + " " + DataType.getOracleType(table.columns.get(0).dataType));

		for (int i = 1; i < this.table.columns.size(); i++) {
			writeLine(",");
			write(this.table.columns.get(i).name + " " + DataType.getOracleType(table.columns.get(i).dataType));
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

		writeLine(",");
		
		write("CONSTRAINT UC_" + table.name + " UNIQUE (" + this.table.columns.get(1).name);
		for (int i = 2; i <= this.table.cardinality; i++) {
			write("," + this.table.columns.get(i).name);
		}
		writeLine(")");
		write("USING INDEX (CREATE INDEX UC_" + table.name + " ON " + table.name + "(" + this.table.columns.get(1).name);
		for (int i = 2; i <= this.table.cardinality; i++) {
			write("," + this.table.columns.get(i).name);
		}
		writeLine(") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND)");
		
		writeLine(", CONSTRAINT PK_" + table.name + " PRIMARY KEY (" + this.table.columns.get(0).name + ")");
		writeLine("USING INDEX (CREATE INDEX PK_" + table.name + " ON " + table.name + "(" + this.table.columns.get(0).name + ") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND)");

		writeLine(") TABLESPACE " + table.myPackage.model.project.databaseName + "_TBL");
		writeLine("/");
		skipLine();
		
		writeLine("-- drop sequence --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP SEQUENCE " + sequenceName + "';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();

		writeLine("-- create sequence --");
		writeLine("CREATE SEQUENCE " + sequenceName + " MINVALUE 0 NOMAXVALUE START WITH 0 INCREMENT BY 1 NOCYCLE");
		writeLine("/");
		skipLine();
	}
	
	/*
	 * create audit table
	 */
	private void createAuditTable()
    {
		
		writeLine("-- drop audit table --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "_AUD';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
		
        writeLine("-- create audit table --");
        writeLine("CREATE TABLE " + table.name + "_AUD");
        writeLine("(");
        writeLine(table.columns.get(0).name + " int NOT NULL,");
        writeLine("REV int NOT NULL,");
        writeLine("REVTYPE smallint NOT NULL,");

        for (int i = 1;i<this.table.columns.size();i++)
        {
            writeLine(this.table.columns.get(i).name + " " + DataType.getOracleType(table.columns.get(i).dataType) + " NULL,");
        }

        writeLine("CONSTRAINT PK_" + table.name + "_AUD PRIMARY KEY (ID, REV),");
        writeLine("CONSTRAINT FK_" + table.name + "_AUD FOREIGN KEY (REV)");
        writeLine("REFERENCES AUDITENTITY (ID)");
        writeLine(") TABLESPACE " + table.myPackage.model.project.databaseName + "_AUD");
        writeLine("/");
        skipLine();
        
        writeLine("CREATE INDEX FK_" + table.name + "_AUD ON " + this.table.name + "_AUD(REV)");
        writeLine("/");
        skipLine();
    }
}
