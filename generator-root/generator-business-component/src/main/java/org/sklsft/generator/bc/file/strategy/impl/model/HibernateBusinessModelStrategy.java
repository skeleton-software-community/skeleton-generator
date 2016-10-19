package org.sklsft.generator.bc.file.strategy.impl.model;

import org.sklsft.generator.bc.file.command.impl.java.api.model.BasicViewBeanFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.api.model.FullViewBeanFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.model.EntityBeanFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class HibernateBusinessModelStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode businessModelTreeNode = new FileWriteCommandTreeNode("Business model");

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			businessModelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode omTreeNode = new FileWriteCommandTreeNode("Entities");
			packageTreeNode.add(omTreeNode);
			for (Bean bean : myPackage.beans) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new EntityBeanFileWriteCommand(bean));
				omTreeNode.add(beanTreeNode);
			}

			FileWriteCommandTreeNode ovTreeNode = new FileWriteCommandTreeNode("View beans");
			packageTreeNode.add(ovTreeNode);
			for (Bean bean : myPackage.beans) {
				if (!bean.isEmbedded) {
					FileWriteCommandTreeNode basicViewTreeNode = new FileWriteCommandTreeNode(new BasicViewBeanFileWriteCommand(bean));
					ovTreeNode.add(basicViewTreeNode);
					
					FileWriteCommandTreeNode fullViewTreeNode = new FileWriteCommandTreeNode(new FullViewBeanFileWriteCommand(bean));
					ovTreeNode.add(fullViewTreeNode);
				}
			}
		}

		return businessModelTreeNode;
	}

}
