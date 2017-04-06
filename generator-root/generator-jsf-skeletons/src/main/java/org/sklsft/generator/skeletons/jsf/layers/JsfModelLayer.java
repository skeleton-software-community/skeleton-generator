package org.sklsft.generator.skeletons.jsf.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.jsf.commands.model.MvcDetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.model.MvcListViewFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class JsfModelLayer extends AbstractLayer {
	
	public JsfModelLayer() {
		super("MVC Model");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode mvcLayerTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode("List view");
		mvcLayerTreeNode.add(listViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			listViewTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MvcListViewFileWriteCommand(bean));
					listViewTreeNode.add(beanTreeNode);
				}
			}
		}
		
		FileWriteCommandTreeNode detailViewTreeNode = new FileWriteCommandTreeNode("Detail view");
		mvcLayerTreeNode.add(detailViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			detailViewTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MvcDetailViewFileWriteCommand(bean));
					detailViewTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return mvcLayerTreeNode;
	}

}
