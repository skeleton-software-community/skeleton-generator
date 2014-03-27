package org.skeleton.generator.bl.services.interfaces;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.model.om.Project;

public interface CodeWriter {
	
	FileWriteCommandTree buildFileWriteCommandTree(Project project);
	
	FileWriteCommandTree buildConfigurationTree(Project project);
	
	void writeCode(FileWriteCommandTree tree);
}
