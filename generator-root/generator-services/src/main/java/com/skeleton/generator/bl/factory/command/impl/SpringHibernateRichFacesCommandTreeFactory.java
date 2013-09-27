package com.skeleton.generator.bl.factory.command.impl;

import com.skeleton.generator.bl.executor.FileWriteCommandTree;
import com.skeleton.generator.bl.executor.FileWriteCommandTreeNode;
import com.skeleton.generator.bl.factory.command.interfaces.FileWriteCommandTreeFactory;
import com.skeleton.generator.model.om.Project;

public class SpringHibernateRichFacesCommandTreeFactory implements FileWriteCommandTreeFactory {

	public FileWriteCommandTree buildTree(Project project) {
		
		FileWriteCommandTree tree = new FileWriteCommandTree(new FileWriteCommandTreeNode(null, project.projectName));
		
		
		
		return tree;
	}
}
