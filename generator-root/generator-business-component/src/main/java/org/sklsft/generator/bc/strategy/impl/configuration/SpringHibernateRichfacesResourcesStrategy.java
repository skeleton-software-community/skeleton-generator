package org.sklsft.generator.bc.strategy.impl.configuration;

import org.sklsft.generator.bc.command.file.impl.ResourcesFileWriteCommand;
import org.sklsft.generator.bc.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Project;

public class SpringHibernateRichfacesResourcesStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, "/jsf/src/",project.projectName + "-webapp"));
		
		return resourcesTreeNode;
	}

}
