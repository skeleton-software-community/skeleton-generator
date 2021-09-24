package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.angular.commands.views.TsDetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.views.TsListViewFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class TypeScriptViewsLayer extends AbstractLayer {
	
	public TypeScriptViewsLayer() {
		super("TypeScript Views");
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

		FileWriteCommandTreeNode viewsTreeNode = new FileWriteCommandTreeNode();
		
		
		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			viewsTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode listViewsTreeNode = new FileWriteCommandTreeNode("List views");
			packageTreeNode.add(listViewsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode(new TsListViewFileWriteCommand(bean));
					listViewsTreeNode.add(listViewTreeNode);
				}
			}
			
			FileWriteCommandTreeNode detailViewsTreeNode = new FileWriteCommandTreeNode("Detail views");
			packageTreeNode.add(detailViewsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode detailViewTreeNode = new FileWriteCommandTreeNode(new TsDetailViewFileWriteCommand(bean));
					detailViewsTreeNode.add(detailViewTreeNode);
				}
			}
		}

		return viewsTreeNode;
	}
}
