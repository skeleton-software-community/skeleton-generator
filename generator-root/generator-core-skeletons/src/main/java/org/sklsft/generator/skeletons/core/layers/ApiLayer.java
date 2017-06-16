package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.core.commands.api.configuration.ApiPomFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.interfaces.BaseServiceInterfaceFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.interfaces.ServiceInterfaceFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.model.BasicViewBeanFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.model.FilterFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.model.FormBeanFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.model.FullViewBeanFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.api.model.SortingFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class ApiLayer extends AbstractLayer {
	
	public ApiLayer() {
		super("API");
	}

	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		return null;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode apiPomTreeNode = new FileWriteCommandTreeNode(new ApiPomFileWriteCommand(project));
		configurationTreeNode.add(apiPomTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode apiTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("Interfaces");
		apiTreeNode.add(interfacesTreeNode);
		
		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			interfacesTreeNode.add(packageTreeNode);		
		
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode baseBeanTreeNode = new FileWriteCommandTreeNode(new BaseServiceInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(baseBeanTreeNode);
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ServiceInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(beanTreeNode);
				}
			}
		}
		
		
		FileWriteCommandTreeNode modelTreeNode = new FileWriteCommandTreeNode("Model");
		apiTreeNode.add(modelTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			modelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode ovTreeNode = new FileWriteCommandTreeNode("Views");
			packageTreeNode.add(ovTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode basicViewTreeNode = new FileWriteCommandTreeNode(new BasicViewBeanFileWriteCommand(bean));
					ovTreeNode.add(basicViewTreeNode);
					
					FileWriteCommandTreeNode fullViewTreeNode = new FileWriteCommandTreeNode(new FullViewBeanFileWriteCommand(bean));
					ovTreeNode.add(fullViewTreeNode);
					
					FileWriteCommandTreeNode formTreeNode = new FileWriteCommandTreeNode(new FormBeanFileWriteCommand(bean));
					ovTreeNode.add(formTreeNode);
				}
			}
			
			FileWriteCommandTreeNode filterTreeNode = new FileWriteCommandTreeNode("Filters");
			packageTreeNode.add(filterTreeNode);		

			for (Bean bean : myPackage.beans) {
				
				if (!bean.isEmbedded && !bean.isOneToOneComponent) {
				
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new FilterFileWriteCommand(bean));
					filterTreeNode.add(beanTreeNode);
				}
			}
			
			FileWriteCommandTreeNode oerderingTreeNode = new FileWriteCommandTreeNode("Orderings");
			packageTreeNode.add(oerderingTreeNode);		

			for (Bean bean : myPackage.beans) {
				
				if (!bean.isEmbedded && !bean.isOneToOneComponent) {
				
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new SortingFileWriteCommand(bean));
					filterTreeNode.add(beanTreeNode);
				}
			}
		}

		
		

		return apiTreeNode;
	}
}
