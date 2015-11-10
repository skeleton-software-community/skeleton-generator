package org.sklsft.generator.bl.services.interfaces;

import org.sklsft.generator.bc.file.command.interfaces.FileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTree;
import org.sklsft.generator.model.domain.Project;

/**
 * When writing a skeleton, we need to :
 * <li>define the files that will be copied from resources at initialization
 * <li>define the configuration files that will be created at initialization (can depend on global project metadata)
 * <li>define the files that will be potentially written at each code generation (fully depends on project metadata)
 * each of these strategies will be represented by a {@link FileWriteCommandTree} tree<br/>
 * the method writeCode takes a tree then executes all the {@link FileWriteCommand} nodes
 * @author Nicolas Thibault
 *
 */
public interface CodeGenerator {
	
	/**
	 * builds the {@link FileWriteCommandTree} tree for files that are going to be copied from resources at initialization
	 * @param project
	 * @return
	 */
	FileWriteCommandTree buildFileImportTree(Project project);
	
	/**
	 * builds the {@link FileWriteCommandTree} tree for configuration files that are going to be written at initialization
	 * @param project
	 * @return
	 */
	FileWriteCommandTree buildConfigurationTree(Project project);
	
	/**
	 * builds the {@link FileWriteCommandTree} tree for files that will be written at each code generation
	 * @param project
	 * @return
	 */
	FileWriteCommandTree buildFileWriteCommandTree(Project project);
	
	/**
	 * executes all the {@link FileWriteCommand} nodes of a {@link FileWriteCommandTree} tree
	 * @param project
	 */
	void generateCode(FileWriteCommandTree tree);
}
