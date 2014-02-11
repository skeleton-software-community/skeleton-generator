package org.skeleton.generator.bc.strategy.impl.database;

import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleMainDefinitionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleTableDefinitionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql.PostgresqlBatchFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql.PostgresqlMainDefinitionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql.PostgresqlTableDefinitionFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

public class DefaultDatabaseStrategy implements LayerStrategy {

	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode("Database Files");

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		FileWriteCommandTreeNode mainFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			mainFileTreeNode = new FileWriteCommandTreeNode(new OracleMainDefinitionFileWriteCommand(project), "Main File");
			break;
			
		case POSTGRESQL:
			mainFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlMainDefinitionFileWriteCommand(project), "Main File");
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(mainFileTreeNode);
		
		
		FileWriteCommandTreeNode batchFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			batchFileTreeNode = new FileWriteCommandTreeNode("No batch File");
			break;
			
		case POSTGRESQL:
			batchFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlBatchFileWriteCommand(project), "Batch File");
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(batchFileTreeNode);
		

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tables) {
				FileWriteCommandTreeNode tableTreeNode = new FileWriteCommandTreeNode(table.name);
				switch (project.databaseEngine) {

				case ORACLE:
					tableTreeNode = new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table), table.name);
					break;
					
				case POSTGRESQL:
					tableTreeNode = new FileWriteCommandTreeNode(new PostgresqlTableDefinitionFileWriteCommand(table), table.name);
					break;

				default:
					throw new IllegalArgumentException("unhandled database");
				}
				packageTreeNode.add(tableTreeNode);
			}
		}
		
		return databaseTreeNode;
	}
}
