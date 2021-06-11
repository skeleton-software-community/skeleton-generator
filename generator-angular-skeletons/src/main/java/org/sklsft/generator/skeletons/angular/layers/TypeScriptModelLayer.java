package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.angular.commands.model.TsBasicViewBeanFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.model.TsFormBeanFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.model.TsFullViewBeanFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class TypeScriptModelLayer extends AbstractLayer {
	
	public TypeScriptModelLayer() {
		super("TypeScript Model");
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

		FileWriteCommandTreeNode modelTreeNode = new FileWriteCommandTreeNode();
		
		
		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			modelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode basicViewsTreeNode = new FileWriteCommandTreeNode("Basic views");
			packageTreeNode.add(basicViewsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode basicViewTreeNode = new FileWriteCommandTreeNode(new TsBasicViewBeanFileWriteCommand(bean));
					basicViewsTreeNode.add(basicViewTreeNode);
				}
			}
			
			FileWriteCommandTreeNode formsTreeNode = new FileWriteCommandTreeNode("Forms");
			packageTreeNode.add(formsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode formTreeNode = new FileWriteCommandTreeNode(new TsFormBeanFileWriteCommand(bean));
					formsTreeNode.add(formTreeNode);
				}
			}
			
			FileWriteCommandTreeNode fullViewsTreeNode = new FileWriteCommandTreeNode("Full views");
			packageTreeNode.add(fullViewsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode fullViewTreeNode = new FileWriteCommandTreeNode(new TsFullViewBeanFileWriteCommand(bean));
					formsTreeNode.add(fullViewTreeNode);
				}
			}
		}

		return modelTreeNode;
	}
}
