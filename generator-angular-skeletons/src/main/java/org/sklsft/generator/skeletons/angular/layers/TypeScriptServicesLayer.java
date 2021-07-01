package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.angular.commands.services.TsRestClientFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class TypeScriptServicesLayer extends AbstractLayer {
	
	public TypeScriptServicesLayer() {
		super("TypeScript Services");
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

			FileWriteCommandTreeNode serviceTreeNode = new FileWriteCommandTreeNode("Services");
			packageTreeNode.add(serviceTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode baseRestClientTreeNode = new FileWriteCommandTreeNode(new TsRestClientFileWriteCommand(bean));
					serviceTreeNode.add(baseRestClientTreeNode);
				}
			}
		}

		return modelTreeNode;
	}
}
