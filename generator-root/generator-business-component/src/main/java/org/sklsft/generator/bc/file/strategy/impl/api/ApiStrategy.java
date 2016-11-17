package org.sklsft.generator.bc.file.strategy.impl.api;

import org.sklsft.generator.bc.file.command.impl.java.api.interfaces.BaseServiceInterfaceFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.interfaces.ServiceInterfaceFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.model.BasicViewBeanFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.model.FilterFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.model.FormBeanFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.model.FullViewBeanFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class ApiStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode apiTreeNode = new FileWriteCommandTreeNode("Api");
		
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
		}

		
		

		return apiTreeNode;
	}

}
