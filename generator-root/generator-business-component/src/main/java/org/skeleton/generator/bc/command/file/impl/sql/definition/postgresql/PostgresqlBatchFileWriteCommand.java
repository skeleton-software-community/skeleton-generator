package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.File;
import java.io.IOException;

import org.skeleton.generator.bc.command.file.impl.batch.BatchFileWriteCommand;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

public class PostgresqlBatchFileWriteCommand extends BatchFileWriteCommand {

	private Project project;

	public PostgresqlBatchFileWriteCommand(Project project) {
		super(project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER, "BUILD");
		this.project = project;
	}

	@Override
	public void writeContent() throws IOException {

		writeLine("psql -h " + this.project.databaseDNS + " -p " + this.project.databasePort + " -U " + this.project.databaseUserName + " -w -d " + this.project.databaseName + " < " + (char)34 + "./MAIN.sql" + (char)34);

        
        for (Package myPackage : this.project.model.packages)
        {
            for (Table table : myPackage.tables)
            {
                writeLine("psql -h " + this.project.databaseDNS + " -p " + this.project.databasePort + " -U " + this.project.databaseUserName + " -w -d " + this.project.databaseName + " < " + (char)34 + "./1/" + myPackage.name.toUpperCase() + "/" + table.name + ".sql" + (char)34);
            }
        }
        
        for (Package myPackage : this.project.model.packages)
        {
            for (Table table : myPackage.tables)
            {
                writeLine("psql -h " + this.project.databaseDNS + " -p " + this.project.databasePort + " -U " + this.project.databaseUserName + " -w -d " + this.project.databaseName + " < " + (char)34 + "./2/" + myPackage.name.toUpperCase() + "/" + table.name + ".sql" + (char)34);
            }
        }

        writeLine("pause");
	}
}
