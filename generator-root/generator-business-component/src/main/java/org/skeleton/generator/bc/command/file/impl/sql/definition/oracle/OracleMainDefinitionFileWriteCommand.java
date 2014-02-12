package org.skeleton.generator.bc.command.file.impl.sql.definition.oracle;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

public class OracleMainDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Project project;

	public OracleMainDefinitionFileWriteCommand(Project project) {
		super(project.sourceFolder + "\\SQL", "MAIN");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {
		for (int i = project.model.packages.size() - 1; i >= 0; i--) {
			for (int j = project.model.packages.get(i).tables.size() - 1; j >= 0; j--) {
				Table table = project.model.packages.get(i).tables.get(j);
				String sequenceName = table.name + "_ID_SEQ";

				writeLine("-- suppression de la table --");
				writeLine("BEGIN");
				writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "';");
				writeLine("EXCEPTION");
				writeLine("WHEN OTHERS THEN NULL;");
				writeLine("END;");
				writeLine("/");
				skipLine();
				
				if (project.audited) {
					writeLine("-- suppression de la table d'audit --");
					writeLine("BEGIN");
					writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "_AUD';");
					writeLine("EXCEPTION");
					writeLine("WHEN OTHERS THEN NULL;");
					writeLine("END;");
					writeLine("/");
					skipLine();
				}

				writeLine("-- suppression des procedures stockees --");
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

				writeLine("-- suppression de la sequence --");
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
		
		writeLine("-- suppression de la table d'audit --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP TABLE AUDITENTITY';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
	
		writeLine("-- table d'audit --");
		writeLine("CREATE TABLE AUDITENTITY");
		writeLine("(");
		writeLine("ID int NOT NULL,");
		writeLine((char)34 + "TIMESTAMP" + (char)34 + " int NOT NULL,");
		writeLine("LOGIN VARCHAR2(255),");
		writeLine("CONSTRAINT PK_AUDITENTITY PRIMARY KEY (ID)");
		writeLine(")");
		writeLine(";");
		
		writeLine("-- suppression de la sequence --");
		writeLine("BEGIN");
		writeLine("EXECUTE IMMEDIATE 'DROP SEQUENCE hibernate_sequence';");
		writeLine("EXCEPTION");
		writeLine("WHEN OTHERS THEN NULL;");
		writeLine("END;");
		writeLine("/");
		skipLine();
		
		writeLine("-- sequence --");
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
		writeLine("/");

	}
}
