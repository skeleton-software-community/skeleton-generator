package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.skeletons.core.commands.database.configuration.DataSourceContextFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleMainDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleTableDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.postgresql.PostgresqlMainDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.postgresql.PostgresqlTableDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.postgresql.PostgresqlTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class DefaultDatabaseLayer extends AbstractLayer {
	
	public DefaultDatabaseLayer() {
		super("Database definition");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode dataSourceContextTreeNode = new FileWriteCommandTreeNode(new DataSourceContextFileWriteCommand(project));
		configurationTreeNode.add(dataSourceContextTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		FileWriteCommandTreeNode mainFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			mainFileTreeNode = new FileWriteCommandTreeNode(new OracleMainDefinitionFileWriteCommand(project));
			break;
			
		case POSTGRESQL:
			mainFileTreeNode = new FileWriteCommandTreeNode(new PostgresqlMainDefinitionFileWriteCommand(project));
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(mainFileTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tables) {
				switch (project.databaseEngine) {

				case ORACLE:
					packageTreeNode.add(new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table)));
					packageTreeNode.add(new FileWriteCommandTreeNode(new OracleTableFkDefinitionFileWriteCommand(table)));
					break;
					
				case POSTGRESQL:
					packageTreeNode.add(new FileWriteCommandTreeNode(new PostgresqlTableDefinitionFileWriteCommand(table)));
					packageTreeNode.add(new FileWriteCommandTreeNode(new PostgresqlTableFkDefinitionFileWriteCommand(table)));
					break;

				default:
					throw new IllegalArgumentException("unhandled database");
				}
			}
		}
		
		return databaseTreeNode;
	}
}
