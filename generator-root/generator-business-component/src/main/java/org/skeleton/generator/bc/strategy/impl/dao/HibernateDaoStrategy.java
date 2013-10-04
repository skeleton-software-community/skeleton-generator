package org.skeleton.generator.bc.strategy.impl.dao;

import org.skeleton.generator.bc.command.file.impl.java.dao.BaseDaoHibernateImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.dao.BaseDaoInterfaceFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.dao.DaoImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.dao.DaoInterfaceFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Package;

public class HibernateDaoStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode persistenceTreeNode = new FileWriteCommandTreeNode("Persistence Layer");

		FileWriteCommandTreeNode baseDAOTreeNode = new FileWriteCommandTreeNode("Base DAO");
		persistenceTreeNode.add(baseDAOTreeNode);

		for (Package myPackage : project.model.packageList) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseDAOTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);
			
			for (Bean bean : myPackage.beanList) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseDaoInterfaceFileWriteCommand(bean), bean.baseDaoInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beanList) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseDaoHibernateImplFileWriteCommand(bean), bean.baseDaoClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode daoTreeNode = new FileWriteCommandTreeNode("DAO");
		persistenceTreeNode.add(daoTreeNode);

		for (Package myPackage : project.model.packageList) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseDAOTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode interfacesTreeNode = new FileWriteCommandTreeNode("interfaces");
			packageTreeNode.add(interfacesTreeNode);

			for (Bean bean : myPackage.beanList) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new DaoInterfaceFileWriteCommand(bean), bean.daoInterfaceName);
					interfacesTreeNode.add(beanTreeNode);
				}
			}

			FileWriteCommandTreeNode implTreeNode = new FileWriteCommandTreeNode("impl");
			packageTreeNode.add(implTreeNode);
			
			for (Bean bean : myPackage.beanList) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new DaoImplFileWriteCommand(bean), bean.baseDaoClassName);
					implTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return persistenceTreeNode;
	}
}
