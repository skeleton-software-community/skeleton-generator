package org.sklsft.generator.bc.file.strategy.impl.bc;

import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.BaseBasicViewMapperFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.BaseFormMapperFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.BasicViewMapperFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.FormMapperFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.mapper.FullViewMapperFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.processor.BaseProcessorImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.processor.ProcessorImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.statemanager.BaseStateManagerImplFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.bc.statemanager.StateManagerImplFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

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
		
		
		FileWriteCommandTreeNode baseProcessorTreeNode = new FileWriteCommandTreeNode("Base processors");
		bcTreeNode.add(baseProcessorTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseProcessorTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseProcessorImplFileWriteCommand(bean));
					baseProcessorTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode processorTreeNode = new FileWriteCommandTreeNode("Processors");
		bcTreeNode.add(processorTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			processorTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ProcessorImplFileWriteCommand(bean));
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
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode basicMapperTreeNode = new FileWriteCommandTreeNode(new BaseBasicViewMapperFileWriteCommand(bean));
					packageTreeNode.add(basicMapperTreeNode);
					
					FileWriteCommandTreeNode formMapperTreeNode = new FileWriteCommandTreeNode(new BaseFormMapperFileWriteCommand(bean));
					packageTreeNode.add(formMapperTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode mapperTreeNode = new FileWriteCommandTreeNode("Mappers");
		bcTreeNode.add(mapperTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			mapperTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode basicMapperTreeNode = new FileWriteCommandTreeNode(new BasicViewMapperFileWriteCommand(bean));
					packageTreeNode.add(basicMapperTreeNode);
					
					FileWriteCommandTreeNode formMapperTreeNode = new FileWriteCommandTreeNode(new FormMapperFileWriteCommand(bean));
					packageTreeNode.add(formMapperTreeNode);
					
					FileWriteCommandTreeNode fullMapperTreeNode = new FileWriteCommandTreeNode(new FullViewMapperFileWriteCommand(bean));
					packageTreeNode.add(fullMapperTreeNode);
				}
			}
		}
		
		return bcTreeNode;
	}
}
