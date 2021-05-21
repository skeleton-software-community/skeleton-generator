package org.sklsft.generator.skeletons.jsf.layers;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.jsf.commands.root.PrimefacesRootPomFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class PrimefacesRootLayer extends AbstractLayer {
	
	public PrimefacesRootLayer() {
		super("Primefaces root layer");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new PrimefacesRootPomFileWriteCommand(project));
		configurationTreeNode.add(rootPomTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode treeNode = new FileWriteCommandTreeNode();
		return treeNode;
	}

}
