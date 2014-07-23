package org.sklsft.generator.bc.strategy.impl.dao;

import org.sklsft.generator.bc.command.file.impl.java.dao.BaseDaoHibernateImplFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.dao.BaseDaoInterfaceFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.dao.DaoImplFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.dao.DaoInterfaceFileWriteCommand;
import org.sklsft.generator.bc.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

public class HibernateDaoStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode persistenceTreeNode = new FileWriteCommandTreeNode("Persistence Layer");

		FileWriteCommandTreeNode baseDAOTreeNode = new FileWriteCommandTreeNode("Base DAO");
		persistenceTreeNode.add(baseDAOTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseDAOTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseDaoInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseDaoHibernateImplFileWriteCommand(bean));
					implTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode daoTreeNode = new FileWriteCommandTreeNode("DAO");
		persistenceTreeNode.add(daoTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseDAOTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new DaoInterfaceFileWriteCommand(bean));
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new DaoImplFileWriteCommand(bean));
					implTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return persistenceTreeNode;
	}
}
