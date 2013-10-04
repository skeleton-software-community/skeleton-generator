package org.skeleton.generator.bc.strategy.impl.database;

import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleMainDefinitionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleTableDefinitionFileWriteCommand;
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

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(mainFileTreeNode);

		for (Package myPackage : project.model.packageList) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tableList) {
				FileWriteCommandTreeNode tableTreeNode = new FileWriteCommandTreeNode(table.name);
				switch (project.databaseEngine) {

				case ORACLE:
					tableTreeNode = new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table), table.name);
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
