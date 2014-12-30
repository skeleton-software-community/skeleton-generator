package org.sklsft.generator.bc.file.strategy.impl.bc;

import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.BaseMapperImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.MapperImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.statemanager.BaseStateManagerImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.statemanager.StateManagerImplFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

public class BusinessComponentStrategy implements LayerStrategy {
	
	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode bcTreeNode = new FileWriteCommandTreeNode("Business components");

		FileWriteCommandTreeNode baseStateManagerTreeNode = new FileWriteCommandTreeNode("Base state managers");
		bcTreeNode.add(baseStateManagerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseStateManagerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseStateManagerImplFileWriteCommand(bean));
					baseStateManagerTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode stateManagerTreeNode = new FileWriteCommandTreeNode("State managers");
		bcTreeNode.add(stateManagerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			stateManagerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new StateManagerImplFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
				}
			}
		}
		
		
		
		FileWriteCommandTreeNode baseMapperTreeNode = new FileWriteCommandTreeNode("Base mappers");
		bcTreeNode.add(baseMapperTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseMapperTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseMapperImplFileWriteCommand(bean));
				packageTreeNode.add(beanTreeNode);
			}
		}

		FileWriteCommandTreeNode mapperTreeNode = new FileWriteCommandTreeNode("Mappers");
		bcTreeNode.add(mapperTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			mapperTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MapperImplFileWriteCommand(bean));
				packageTreeNode.add(beanTreeNode);
			}
		}
		
		return bcTreeNode;
	}
}
