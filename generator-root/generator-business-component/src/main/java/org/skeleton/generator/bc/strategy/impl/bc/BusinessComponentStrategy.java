package org.skeleton.generator.bc.strategy.impl.bc;

import org.skeleton.generator.bc.command.file.impl.java.bc.mapper.BaseMapperImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.mapper.BaseMapperInterfaceFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.mapper.MapperImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.mapper.MapperInterfaceFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.statemanager.BaseStateManagerImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.statemanager.BaseStateManagerInterfaceFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.statemanager.StateManagerImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.bc.statemanager.StateManagerInterfaceFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;

public class BusinessComponentStrategy implements LayerStrategy {
	
	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode bcTreeNode = new FileWriteCommandTreeNode("Business components");

		FileWriteCommandTreeNode baseStateManagerTreeNode = new FileWriteCommandTreeNode("Base state managers");
		bcTreeNode.add(baseStateManagerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseStateManagerTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseStateManagerInterfaceFileWriteCommand(bean), bean.baseStateManagerInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseStateManagerImplFileWriteCommand(bean), bean.baseStateManagerClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode stateManagerTreeNode = new FileWriteCommandTreeNode("State managers");
		bcTreeNode.add(stateManagerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseStateManagerTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new StateManagerInterfaceFileWriteCommand(bean), bean.stateManagerInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new StateManagerImplFileWriteCommand(bean), bean.stateManagerClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}
		
		
		
		FileWriteCommandTreeNode baseMapperTreeNode = new FileWriteCommandTreeNode("Base mappers");
		bcTreeNode.add(baseMapperTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseMapperTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseMapperInterfaceFileWriteCommand(bean), bean.baseMapperInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseMapperImplFileWriteCommand(bean), bean.baseMapperClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode mapperTreeNode = new FileWriteCommandTreeNode("Mappers");
		bcTreeNode.add(mapperTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseMapperTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MapperInterfaceFileWriteCommand(bean), bean.mapperInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MapperImplFileWriteCommand(bean), bean.mapperClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return bcTreeNode;
	}
}
