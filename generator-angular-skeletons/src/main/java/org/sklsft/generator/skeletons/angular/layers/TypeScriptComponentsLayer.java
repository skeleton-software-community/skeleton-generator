package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.angular.commands.pages.HtmlListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.ScssListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsAppRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsListModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsListRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class TypeScriptComponentsLayer extends AbstractLayer {
	
	public TypeScriptComponentsLayer() {
		super("TypeScript Pages");
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
		
		FileWriteCommandTreeNode appRouting = new FileWriteCommandTreeNode(new TsAppRoutingModuleFileWriteCommand(project));
		modelTreeNode.add(appRouting);
		
		
		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			modelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode pagesTreeNode = new FileWriteCommandTreeNode("Pages");
			packageTreeNode.add(pagesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode listModule = new FileWriteCommandTreeNode(new TsListModuleFileWriteCommand(bean));
					pagesTreeNode.add(listModule);
					
					FileWriteCommandTreeNode listRoutingModule = new FileWriteCommandTreeNode(new TsListRoutingModuleFileWriteCommand(bean));
					pagesTreeNode.add(listRoutingModule);
					
					FileWriteCommandTreeNode listComponent = new FileWriteCommandTreeNode(new TsListComponentFileWriteCommand(bean));
					pagesTreeNode.add(listComponent);
					
					FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssListComponentFileWriteCommand(bean));
					pagesTreeNode.add(scssComponent);
					
					FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlListComponentFileWriteCommand(bean));
					pagesTreeNode.add(htmlComponent);
				}
			}
		}

		return modelTreeNode;
	}
}
