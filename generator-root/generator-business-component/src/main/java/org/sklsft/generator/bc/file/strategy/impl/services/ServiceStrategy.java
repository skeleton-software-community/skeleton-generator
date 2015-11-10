package org.sklsft.generator.bc.file.strategy.impl.services;

import org.sklsft.generator.bc.file.command.impl.java.services.BaseServiceImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.services.BaseServiceInterfaceFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.services.ServiceImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.services.ServiceInterfaceFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class ServiceStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode serviceLayerTreeNode = new FileWriteCommandTreeNode("Services Layer");

		FileWriteCommandTreeNode baseServiceTreeNode = new FileWriteCommandTreeNode("Base Services");
		serviceLayerTreeNode.add(baseServiceTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseServiceTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseServiceInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseServiceImplFileWriteCommand(bean));
					implTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode serviceTreeNode = new FileWriteCommandTreeNode("Services");
		serviceLayerTreeNode.add(serviceTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseServiceTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ServiceInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ServiceImplFileWriteCommand(bean));
					implTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return serviceLayerTreeNode;
	}
}
