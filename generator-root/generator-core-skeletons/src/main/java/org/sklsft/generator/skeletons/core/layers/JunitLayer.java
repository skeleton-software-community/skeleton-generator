package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.LogbackTestFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.SpringTestFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.SpringTestRepositoryFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.TestPomFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.TestPropertiesFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.resources.JUnitDataInitializerFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.resources.SetupTestFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class JunitLayer extends AbstractLayer {
	
	public JunitLayer() {
		super("JUnit tests");
	}

	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode dataInitializerTreeNode = new FileWriteCommandTreeNode(new JUnitDataInitializerFileWriteCommand(project));
		resourcesTreeNode.add(dataInitializerTreeNode);
		
		FileWriteCommandTreeNode testSetupTreeNode = new FileWriteCommandTreeNode(new SetupTestFileWriteCommand(project));
		resourcesTreeNode.add(testSetupTreeNode);
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode testPomTreeNode = new FileWriteCommandTreeNode(new TestPomFileWriteCommand(project));
		configurationTreeNode.add(testPomTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringTestRepositoryFileWriteCommand(project));
		configurationTreeNode.add(springTestRepositoryTreeNode);
		
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode(new SpringTestFileWriteCommand(project));
		configurationTreeNode.add(springTestTreeNode);
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new LogbackTestFileWriteCommand(project));
		configurationTreeNode.add(logbackTestTreeNode);
		
		FileWriteCommandTreeNode testPropertiesTreeNode = new FileWriteCommandTreeNode(new TestPropertiesFileWriteCommand(project));
		configurationTreeNode.add(testPropertiesTreeNode);
		
		return configurationTreeNode;
	}
	
	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		return null;
	}
}
