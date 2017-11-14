package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.skeletons.core.commands.database.configuration.postgresql.PostgresqlDataSourceContextFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.configuration.postgresql.PostgresqlMainDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.postgresql.PostgresqlTableDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.postgresql.PostgresqlTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class PostgresDatabaseLayer extends AbstractLayer {
	
	public PostgresDatabaseLayer() {
		super("Database definition");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode dataSourceContextTreeNode = new FileWriteCommandTreeNode(new PostgresqlDataSourceContextFileWriteCommand(project));
		configurationTreeNode.add(dataSourceContextTreeNode);
		
		FileWriteCommandTreeNode mainFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlMainDefinitionFileWriteCommand(project));
		configurationTreeNode.add(mainFileTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tables) {
				packageTreeNode.add(new FileWriteCommandTreeNode(new PostgresqlTableDefinitionFileWriteCommand(table)));
				packageTreeNode.add(new FileWriteCommandTreeNode(new PostgresqlTableFkDefinitionFileWriteCommand(table)));
			}
		}
		
		return databaseTreeNode;
	}
}
