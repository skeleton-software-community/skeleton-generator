package org.sklsft.generator.bc.file.strategy.impl.services;

import org.sklsft.generator.bc.file.command.impl.java.services.BaseServiceImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.services.ServiceImplFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class ServiceStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode serviceLayerTreeNode = new FileWriteCommandTreeNode("Services Layer");

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			serviceLayerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode baseBeanTreeNode = new FileWriteCommandTreeNode(new BaseServiceImplFileWriteCommand(bean));
					packageTreeNode.add(baseBeanTreeNode);
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ServiceImplFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return serviceLayerTreeNode;
	}
}
