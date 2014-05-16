package org.skeleton.generator.bc.factory.command.file.interfaces;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.model.om.Project;

/**
 * 
 * @author Nicolas Thibault
 *
 */
public interface FileWriteCommandTreeFactory {

	FileWriteCommandTree buildFileImportTree(Project project);
	
	FileWriteCommandTree buildConfigurationTree(Project project);
	
	FileWriteCommandTree buildTree(Project project);
}
