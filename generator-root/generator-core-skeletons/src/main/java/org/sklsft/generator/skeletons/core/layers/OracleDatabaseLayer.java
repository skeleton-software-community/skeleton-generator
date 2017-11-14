package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.skeletons.core.commands.database.configuration.oracle.OracleDataSourceContextFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleMainDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleTableDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.database.oracle.OracleTableFkDefinitionFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class OracleDatabaseLayer extends AbstractLayer {
	
	public OracleDatabaseLayer() {
		super("Database definition");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode dataSourceContextTreeNode = new FileWriteCommandTreeNode(new OracleDataSourceContextFileWriteCommand(project));
		configurationTreeNode.add(dataSourceContextTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		FileWriteCommandTreeNode mainFileTreeNode = new FileWriteCommandTreeNode(new OracleMainDefinitionFileWriteCommand(project));			
		definitionFilesTreeNode.add(mainFileTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tables) {
				packageTreeNode.add(new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table)));
				packageTreeNode.add(new FileWriteCommandTreeNode(new OracleTableFkDefinitionFileWriteCommand(table)));
			}
		}
		
		return databaseTreeNode;
	}
}
