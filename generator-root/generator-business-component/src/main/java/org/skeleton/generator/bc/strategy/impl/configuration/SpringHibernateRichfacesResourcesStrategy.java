package org.skeleton.generator.bc.strategy.impl.configuration;

import org.skeleton.generator.bc.command.file.impl.templatized.ResourcesFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesResourcesStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, "jsf/src/",project.projectName + "-webapp"),"Resources");
		
		return resourcesTreeNode;
	}

}
