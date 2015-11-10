package org.sklsft.generator.bc.file.strategy.impl.configuration;

import org.sklsft.generator.bc.file.command.impl.ResourcesFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Project;

public class Richfaces3ResourcesStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, "/richfaces3/src/",project.projectName + "-webapp"));
		
		return resourcesTreeNode;
	}

}
