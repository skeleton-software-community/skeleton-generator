package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.model.om.Package;

public class PostgresqlBatchFileWriteCommand extends SqlFileWriteCommand {

	private Project project;

	public PostgresqlBatchFileWriteCommand(Project project) {
		super(project.sourceFolder + "\\SQL", "MAIN");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {

		writeLine("psql -h " + this.project.serverDNS + " -p " + this.project.serverPort + " -U " + this.project.userName + " -w -d " + this.project.databaseName.toLowerCase() + " > " + (char)34 + "./LOG/MAIN.txt" + (char)34 + " < " + (char)34 + "./MAIN.sql" + (char)34);

        
        for (Package myPackage : this.project.model.packageList)
        {
            for (Table table : myPackage.tableList)
            {
                writeLine("psql -h " + this.project.serverDNS + " -p " + this.project.serverPort + " -U " + this.project.userName + " -w -d " + this.project.databaseName.toLowerCase() + " > " + (char)34 + "./LOG/" + myPackage.name.toUpperCase() + "/" + table.name + ".txt" + (char)34 + " < " + (char)34 + "./" + myPackage.name.toUpperCase() + "/" + table.name + ".sql" + (char)34);
            }
        }

        writeLine("pause");
	}
}
