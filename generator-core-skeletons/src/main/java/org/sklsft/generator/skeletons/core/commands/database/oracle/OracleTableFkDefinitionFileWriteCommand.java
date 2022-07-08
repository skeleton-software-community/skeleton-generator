package org.sklsft.generator.skeletons.core.commands.database.oracle;

import java.io.File;
import java.io.IOException;

import org.sklsft.generator.components.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Index;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.domain.database.UniqueConstraint;
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
		
		createConstraints();
		
		createTableFks();
		
		createIndexes();

		writeNotOverridableContent();

		skipLine();
	}
	
	private void createConstraints() {
		writeLine("-- table unique constraints --");
		for (UniqueConstraint constraint:table.uniqueConstraints) {
			write("ALTER TABLE " + table.name + " ADD CONSTRAINT " + constraint.name + " UNIQUE (" + constraint.columns.get(0).name);
			for (int i = 1; i < constraint.columns.size(); i++) {
				write("," + constraint.columns.get(i).name);
			}
			write(")");
			write(" USING INDEX (CREATE INDEX " + constraint.index.name + " ON " + table.name + "(" + constraint.columns.get(0).name);
			for (int i = 1; i < constraint.columns.size(); i++) {
				write("," + constraint.columns.get(i).name);
			}
			write(")");
			
			if (table.myPackage.model.project.indexesTableSpace != null) {
				write(" TABLESPACE " + table.myPackage.model.project.indexesTableSpace);
			}
			write(")");
			skipLine();
			writeLine("/");
            skipLine();
		}
	}

	private void createTableFks() {

		writeLine("-- table foreign keys --");
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
	}
		
	/**
	 * create indexes
	 */
	private void createIndexes() {
		
		writeLine("-- table indexes --");
		for (Index index:table.indexes) {
			if (index.uniqueConstraint == null) {
                write("CREATE INDEX " + index.name + " ON " + this.table.name + "(" + index.columns.get(0).name + ")");
                if (table.myPackage.model.project.indexesTableSpace != null) {
        			write(" TABLESPACE " + table.myPackage.model.project.indexesTableSpace);
        		}
                skipLine();
                writeLine("/");
                skipLine();
            }
		}
    }
}
