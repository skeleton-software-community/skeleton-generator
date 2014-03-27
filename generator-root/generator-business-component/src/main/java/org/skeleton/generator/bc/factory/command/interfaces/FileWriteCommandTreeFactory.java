package org.skeleton.generator.bc.factory.command.interfaces;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.model.om.Project;

public interface FileWriteCommandTreeFactory {

	FileWriteCommandTree buildTree(Project project);
	
	FileWriteCommandTree buildConfigurationTree(Project project);
}
