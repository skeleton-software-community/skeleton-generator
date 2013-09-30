package org.skeleton.generator.bc.command.file.impl.sql.definition.oracle;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

public class OracleMainDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Project project;

    public OracleMainDefinitionFileWriteCommand(Project project)
    {
    	super(project.sourceFolder + "\\SQL","MAIN");
        this.project = project;
    }
    
    
    @Override
	public void writeContent() throws IOException {
    for (int i=project.model.packageList.size()-1;i>=0;i--)
    {
        for (int j=project.model.packageList.get(i).tableList.size()-1;j>=0;j--)
        {
            Table table = project.model.packageList.get(i).tableList.get(j);
            String sequenceName = table.name + "_ID_SEQ";

            writeLine("-- suppression de la table --");
            writeLine("BEGIN");
            writeLine("EXECUTE IMMEDIATE 'DROP TABLE " + table.name + "';");
            writeLine("EXCEPTION");
            writeLine("WHEN OTHERS THEN NULL;");
            writeLine("END;");
            writeLine("/");
            skipLine();

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
}
}
