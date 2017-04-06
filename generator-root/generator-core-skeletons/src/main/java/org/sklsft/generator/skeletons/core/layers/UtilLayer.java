package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.core.commands.util.configuration.UtilPomFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class UtilLayer extends AbstractLayer {
	
	public UtilLayer() {
		super("Utils");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode utilPomTreeNode = new FileWriteCommandTreeNode(new UtilPomFileWriteCommand(project));
		configurationTreeNode.add(utilPomTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		return null;
	}
}
