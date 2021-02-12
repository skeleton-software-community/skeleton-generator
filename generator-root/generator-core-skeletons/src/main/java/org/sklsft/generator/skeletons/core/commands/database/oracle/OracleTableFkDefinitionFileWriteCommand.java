package org.sklsft.generator.skeletons.core.commands.database.oracle;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.skeletons.commands.impl.typed.SqlFileWriteCommand;
import org.sklsft.generator.skeletons.core.database.OracleHandler;

public class OracleTableFkDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;

	/*
	 * constructor
	 */
	public OracleTableFkDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.workspaceFolder + File.separator + DatabaseHandlerDiscovery.getBuildScriptFolder(OracleHandler.NAME) + File.separator + "2" + File.separator + table.myPackage.name.toUpperCase().replace(".", File.separator), table.originalName);

		this.table = table;
	}

	@Override
	public void writeContent() throws IOException {
		
		createTableFks();

		writeNotOverridableContent();

		skipLine();
	}

	private void createTableFks() {

		writeLine("-- table foreign keys and indexes --");
		int i = 0;
		for (Column column:table.columns) {
			if (column.referenceTable != null) {
				write("ALTER TABLE " + table.name + " ADD CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + column.name + ") REFERENCES " + column.referenceTable.name);
				if (this.table.columns.get(i).deleteCascade) {
					write(" ON DELETE CASCADE");
				}
				skipLine();
				writeLine("/");
                skipLine();
			}
			i++;
		}
		
		i = 0;
		for (Column column:table.columns) {
			if (column.referenceTable != null) {
                write("CREATE INDEX IDX_" + table.name + "_F" + i + " ON " + this.table.name + "(" + column.name + ")");
                if (table.myPackage.model.project.indexesTableSpace != null) {
        			write(" TABLESPACE " + table.myPackage.model.project.indexesTableSpace);
        		}
                skipLine();
                writeLine("/");
                skipLine();
            }
			i++;
        }
	}
}
