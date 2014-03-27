package org.skeleton.generator.bl.services.impl;

import org.skeleton.generator.bc.executor.FileWriteCommandTree;
import org.skeleton.generator.bc.factory.command.impl.FileWriteCommandTreeFactoryBuilder;
import org.skeleton.generator.bc.factory.command.interfaces.FileWriteCommandTreeFactory;
import org.skeleton.generator.bl.services.interfaces.CodeWriter;
import org.skeleton.generator.model.om.Project;
import org.springframework.stereotype.Component;

@Component
public class CodeWriterImpl implements CodeWriter {

	@Override
	public FileWriteCommandTree buildFileWriteCommandTree(Project project) {

		FileWriteCommandTreeFactory factory = FileWriteCommandTreeFactoryBuilder.getFileWriteCommandTreeFactory(project);
		
		return factory.buildTree(project);
	}
	
	@Override
	public FileWriteCommandTree buildConfigurationTree(Project project) {

		FileWriteCommandTreeFactory factory = FileWriteCommandTreeFactoryBuilder.getFileWriteCommandTreeFactory(project);
		
		return factory.buildConfigurationTree(project);
	}

	@Override
	public void writeCode(FileWriteCommandTree tree) {

		tree.launch();
		
	}

}
