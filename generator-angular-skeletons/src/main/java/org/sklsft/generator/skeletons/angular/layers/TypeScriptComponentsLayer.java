package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.angular.commands.pages.TsAppRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.HtmlDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.ScssDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.TsDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.HtmlListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.ScssListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.TsListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.modal.HtmlModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.modal.ScssModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.modal.TsModalComponentFileWriteCommand;
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
			
			FileWriteCommandTreeNode modulesTreeNode = new FileWriteCommandTreeNode("Moduls");
			packageTreeNode.add(modulesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode module = new FileWriteCommandTreeNode(new TsModuleFileWriteCommand(bean));
					modulesTreeNode.add(module);
					
					FileWriteCommandTreeNode routingModule = new FileWriteCommandTreeNode(new TsRoutingModuleFileWriteCommand(bean));
					modulesTreeNode.add(routingModule);
					
				}
			}

			FileWriteCommandTreeNode listPagesTreeNode = new FileWriteCommandTreeNode("List Pages");
			packageTreeNode.add(listPagesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsListComponentFileWriteCommand(bean));
					listPagesTreeNode.add(tsComponent);
					
					FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssListComponentFileWriteCommand(bean));
					listPagesTreeNode.add(scssComponent);
					
					FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlListComponentFileWriteCommand(bean));
					listPagesTreeNode.add(htmlComponent);
				}
			}
			
			FileWriteCommandTreeNode pagesTreeNode = new FileWriteCommandTreeNode("Details Pages");
			packageTreeNode.add(pagesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsDetailsComponentFileWriteCommand(bean));
					pagesTreeNode.add(tsComponent);
					
					FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssDetailsComponentFileWriteCommand(bean));
					pagesTreeNode.add(scssComponent);
					
					FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlDetailsComponentFileWriteCommand(bean));
					pagesTreeNode.add(htmlComponent);
				}
			}
			
			FileWriteCommandTreeNode modalsTreeNode = new FileWriteCommandTreeNode("Madals");
			packageTreeNode.add(modalsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsModalComponentFileWriteCommand(bean));
					modalsTreeNode.add(tsComponent);
					
					FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssModalComponentFileWriteCommand(bean));
					modalsTreeNode.add(scssComponent);
					
					FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlModalComponentFileWriteCommand(bean));
					modalsTreeNode.add(htmlComponent);
				}
			}
		}

		return modelTreeNode;
	}
}
