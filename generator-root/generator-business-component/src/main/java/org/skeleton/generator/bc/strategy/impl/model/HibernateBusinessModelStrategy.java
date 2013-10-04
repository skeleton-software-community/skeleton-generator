package org.skeleton.generator.bc.strategy.impl.model;

import org.skeleton.generator.bc.command.file.impl.java.model.EntityBeanFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.model.ViewBeanFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;

public class HibernateBusinessModelStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode businessModelTreeNode = new FileWriteCommandTreeNode("Business model");

		for (Package myPackage : project.model.packageList) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			businessModelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode omTreeNode = new FileWriteCommandTreeNode("Entities");
			packageTreeNode.add(omTreeNode);
			for (Bean bean : myPackage.beanList) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new EntityBeanFileWriteCommand(bean), bean.className);
				omTreeNode.add(beanTreeNode);
			}

			FileWriteCommandTreeNode ovTreeNode = new FileWriteCommandTreeNode("View beans");
			packageTreeNode.add(ovTreeNode);
			for (Bean bean : myPackage.beanList) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanFileWriteCommand(bean), bean.viewClassName);
				ovTreeNode.add(beanTreeNode);
			}
		}

		return businessModelTreeNode;
	}

}
