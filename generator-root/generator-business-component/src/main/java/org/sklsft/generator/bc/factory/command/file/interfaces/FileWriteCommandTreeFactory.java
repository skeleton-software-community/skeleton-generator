package org.sklsft.generator.bc.factory.command.file.interfaces;

import org.sklsft.generator.bc.executor.FileWriteCommandTree;
import org.sklsft.generator.model.om.Project;

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
