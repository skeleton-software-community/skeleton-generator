package org.skeleton.generator.bc.factory.command.impl;

import org.skeleton.generator.bc.command.file.impl.java.model.EntityBeanFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.model.ViewBeanFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleMainDefinitionFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.sql.definition.oracle.OracleTableDefinitionFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.factory.command.interfaces.FileWriteCommandTreeFactory;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;

public class SpringHibernateRichFacesCommandTreeFactory implements FileWriteCommandTreeFactory {

	public FileWriteCommandTree buildTree(Project project) {

		FileWriteCommandTreeNode rootNode = new FileWriteCommandTreeNode(project.projectName);

		FileWriteCommandTree tree = new FileWriteCommandTree(rootNode);

		addDatabaseNode(rootNode, project);

		addBusinessModelNode(rootNode, project);

		return tree;
	}
	

	private void addDatabaseNode(FileWriteCommandTreeNode rootNode, Project project) {

		FileWriteCommandTreeNode databaseTreeNode = new FileWriteCommandTreeNode("Database Files");
		rootNode.add(databaseTreeNode);

		FileWriteCommandTreeNode definitionFilesTreeNode = new FileWriteCommandTreeNode("Definition Files");
		databaseTreeNode.add(definitionFilesTreeNode);

		FileWriteCommandTreeNode mainFileTreeNode;
		switch (project.databaseEngine) {
		case ORACLE:
			mainFileTreeNode = new FileWriteCommandTreeNode(new OracleMainDefinitionFileWriteCommand(project), "Main File");
			break;

		default:
			throw new IllegalArgumentException("unhandled database");
		}
		definitionFilesTreeNode.add(mainFileTreeNode);

		for (Package myPackage : project.model.packageList) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			definitionFilesTreeNode.add(packageTreeNode);
			for (Table table : myPackage.tableList) {
				FileWriteCommandTreeNode tableTreeNode = new FileWriteCommandTreeNode(table.name);
				switch (project.databaseEngine) {

				case ORACLE:
					tableTreeNode = new FileWriteCommandTreeNode(new OracleTableDefinitionFileWriteCommand(table), table.name);
					break;

				default:
					throw new IllegalArgumentException("unhandled database");
				}
				packageTreeNode.add(tableTreeNode);
			}
		}

	}

	
	private void addBusinessModelNode(FileWriteCommandTreeNode rootNode, Project project) {

		FileWriteCommandTreeNode businessModelTreeNode = new FileWriteCommandTreeNode("Business model");
		rootNode.add(businessModelTreeNode);

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

	}
}
