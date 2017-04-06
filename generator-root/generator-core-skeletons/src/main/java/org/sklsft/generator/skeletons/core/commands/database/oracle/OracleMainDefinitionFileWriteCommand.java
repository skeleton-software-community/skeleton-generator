package org.sklsft.generator.skeletons.core.commands.database.oracle;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.skeletons.commands.impl.typed.SqlFileWriteCommand;

public class OracleMainDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Project project;

	public OracleMainDefinitionFileWriteCommand(Project project) {
		super(project.sourceFolder + File.separator + "SQL" + File.separator + "BUILD", "MAIN");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {
		
		writeLine("-- drop foreign keys --");
		for (Package myPackage : project.model.packages) {
			for (Table table : myPackage.tables) {
				
				for (int i = 1; i < table.columns.size(); i++) {
					if (table.columns.get(i).referenceTable != null) {
						writeLine("BEGIN");
						writeLine("EXECUTE IMMEDIATE 'ALTER TABLE " + table.name + " DROP CONSTRAINT FK_" + table.name + "_" + i + "';");
						writeLine("EXCEPTION");
						writeLine("WHEN OTHERS THEN NULL;");
						writeLine("END;");
						writeLine("/");
		                skipLine();
					}
				}
			}
		}
				
		for (Package myPackage : project.model.packages) {
			for (Table table : myPackage.tables) {
				writeLine("-- drop definitions for " + table.name + " --");
				String sequenceName = table.name + "_ID_SEQ";
				
				writeLine("-- drop table --");
				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();
				
				if (project.audited) {
					writeLine("-- drop audit table --");
					writeLine("BEGIN");
					writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "_AUD';");
					writeLine("EXCEPTION");
					writeLine("WHEN OTHERS THEN NULL;");
					writeLine("END;");
					writeLine("/");
					skipLine();
				}

				writeLine("-- drop stored procedures --");
				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE get_" + table.name.toLowerCase() + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE find_" + table.name.toLowerCase() + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE ins_" + table.name.toLowerCase() + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE ins_" + table.name.toLowerCase() + "_bc';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE upd_" + table.name.toLowerCase() + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE upd_" + table.name.toLowerCase() + "_bc';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE del_" + table.name.toLowerCase() + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();

				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP PROCEDURE del_" + table.name.toLowerCase() + "_bc';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
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
			}
		}
		
		if (project.audited) {
        	skipLine();
        	createAuditTable();
        	skipLine();
        }
		
		writeNotOverridableContent();
	}
	
	private void createAuditTable() {
		
		writeLine("-- drop global audit table --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE AUDITENTITY';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
	
		writeLine("-- create global audit table --");
		writeLine("CREATE TABLE AUDITENTITY");
		writeLine("(");
		writeLine("ID int NOT NULL,");
		writeLine((char)34 + "TIMESTAMP" + (char)34 + " int NOT NULL,");
		writeLine("LOGIN VARCHAR2(255),");
		writeLine("CONSTRAINT PK_AUDITENTITY PRIMARY KEY (ID)");
		writeLine(") TABLESPACE " + project.databaseName + "_AUD");
		writeLine("/");
		skipLine();
		
		writeLine("-- drop hibernate sequence --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP SEQUENCE hibernate_sequence';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
		
		writeLine("-- create hibernate sequence --");
		writeLine("CREATE SEQUENCE hibernate_sequence MINVALUE 1 NOMAXVALUE START WITH 1 INCREMENT BY 1 NOCYCLE");
		writeLine("/");
		skipLine();
		
		writeLine("-- epoch in millis --");
		writeLine("CREATE OR REPLACE FUNCTION current_millis RETURN integer");
		writeLine("IS");
		writeLine("BEGIN");
		writeLine("return extract(day    from (systimestamp - timestamp '1970-01-01 00:00:00')) * 86400000");
		writeLine("+ (extract(hour   from (systimestamp - timestamp '1970-01-01 00:00:00'))-1) * 3600000");
		writeLine("+ extract(minute from (systimestamp - timestamp '1970-01-01 00:00:00')) * 60000");
		writeLine("+ extract(second from (systimestamp - timestamp '1970-01-01 00:00:00')) * 1000;");
		writeLine("END;");

	}
}
