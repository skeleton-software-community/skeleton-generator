package org.skeleton.generator.bc.factory.command.impl;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.factory.command.interfaces.FileWriteCommandTreeFactory;
import org.skeleton.generator.model.om.Project;


public class SpringHibernateRichFacesCommandTreeFactory implements FileWriteCommandTreeFactory {

	public FileWriteCommandTree buildTree(Project project) {
		
		FileWriteCommandTree tree = new FileWriteCommandTree(new FileWriteCommandTreeNode(null, project.projectName));
		
		
		
		return tree;
	}
}
