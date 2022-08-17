package org.sklsft.generator.skeletons.angular.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.angular.commands.pages.TsAppRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.TsRoutingModuleFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.HtmlDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.ScssDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.details.TsDetailsComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.HtmlListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.HtmlOneToManyComponentListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.HtmlOneToManyListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.ScssListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.ScssOneToManyComponentListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.ScssOneToManyListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.TsListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.TsOneToManyComponentListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.list.TsOneToManyListComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.menu.HtmlMenuComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.menu.ScssMenuComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.menu.TsMenuComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.HtmlModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.HtmlOneToManyComponentModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.HtmlOneToManyModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.ScssModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.ScssOneToManyComponentModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.ScssOneToManyModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.TsModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.TsOneToManyComponentModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.angular.commands.pages.modal.TsOneToManyModalComponentFileWriteCommand;
import org.sklsft.generator.skeletons.commands.impl.ResourcesFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class TypeScriptComponentsLayer extends AbstractLayer {
	
	public TypeScriptComponentsLayer() {
		super("TypeScript Pages");
	}

	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode copyResourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, getClass(), "/angular",project.projectName + "-ui"));
		resourcesTreeNode.add(copyResourcesTreeNode);
		
		return resourcesTreeNode;
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
			
			FileWriteCommandTreeNode modulesTreeNode = new FileWriteCommandTreeNode("Modules");
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
			
			FileWriteCommandTreeNode modalsTreeNode = new FileWriteCommandTreeNode("Modals");
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
			
			FileWriteCommandTreeNode menusTreeNode = new FileWriteCommandTreeNode("Menus");
			packageTreeNode.add(menusTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsMenuComponentFileWriteCommand(bean));
					menusTreeNode.add(tsComponent);
					
					FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssMenuComponentFileWriteCommand(bean));
					menusTreeNode.add(scssComponent);
					
					FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlMenuComponentFileWriteCommand(bean));
					menusTreeNode.add(htmlComponent);
				}
			}
			
			FileWriteCommandTreeNode oneToManyComponentListPagesTreeNode = new FileWriteCommandTreeNode("One to many component list Pages");
			packageTreeNode.add(oneToManyComponentListPagesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
					
						FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsOneToManyComponentListComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentListPagesTreeNode.add(tsComponent);
						
						FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssOneToManyComponentListComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentListPagesTreeNode.add(scssComponent);
						
						FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlOneToManyComponentListComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentListPagesTreeNode.add(htmlComponent);
					}
				}
			}
			
			FileWriteCommandTreeNode oneToManyComponentModalsTreeNode = new FileWriteCommandTreeNode("One to many component modals");
			packageTreeNode.add(oneToManyComponentModalsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
					
						FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsOneToManyComponentModalComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentModalsTreeNode.add(tsComponent);
						
						FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssOneToManyComponentModalComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentModalsTreeNode.add(scssComponent);
						
						FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlOneToManyComponentModalComponentFileWriteCommand(oneToManyComponent));
						oneToManyComponentModalsTreeNode.add(htmlComponent);
					}
				}
			}
			
			FileWriteCommandTreeNode oneToManyListPagesTreeNode = new FileWriteCommandTreeNode("One to many list Pages");
			packageTreeNode.add(oneToManyListPagesTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					for (OneToMany oneToMany:bean.oneToManyList) {
					
						FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsOneToManyListComponentFileWriteCommand(oneToMany));
						oneToManyListPagesTreeNode.add(tsComponent);
						
						FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssOneToManyListComponentFileWriteCommand(oneToMany));
						oneToManyListPagesTreeNode.add(scssComponent);
						
						FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlOneToManyListComponentFileWriteCommand(oneToMany));
						oneToManyListPagesTreeNode.add(htmlComponent);
					}
				}
			}
			
			FileWriteCommandTreeNode oneToManyModalsTreeNode = new FileWriteCommandTreeNode("One to many modals");
			packageTreeNode.add(oneToManyModalsTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					for (OneToMany oneToMany:bean.oneToManyList) {
					
						FileWriteCommandTreeNode tsComponent = new FileWriteCommandTreeNode(new TsOneToManyModalComponentFileWriteCommand(oneToMany));
						oneToManyModalsTreeNode.add(tsComponent);
						
						FileWriteCommandTreeNode scssComponent = new FileWriteCommandTreeNode(new ScssOneToManyModalComponentFileWriteCommand(oneToMany));
						oneToManyModalsTreeNode.add(scssComponent);
						
						FileWriteCommandTreeNode htmlComponent = new FileWriteCommandTreeNode(new HtmlOneToManyModalComponentFileWriteCommand(oneToMany));
						oneToManyModalsTreeNode.add(htmlComponent);
					}
				}
			}
		}

		return modelTreeNode;
	}
}
