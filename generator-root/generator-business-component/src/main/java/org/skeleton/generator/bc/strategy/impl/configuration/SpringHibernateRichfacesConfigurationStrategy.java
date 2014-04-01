package org.skeleton.generator.bc.strategy.impl.configuration;

import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesRootPomFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Project;

public class SpringHibernateRichfacesConfigurationStrategy  implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode("Configuration");
		
		FileWriteCommandTreeNode pomTreeNode = new FileWriteCommandTreeNode("pom files");
		configurationTreeNode.add(pomTreeNode);
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRootPomFileWriteCommand(project),"root pom.xml");
		pomTreeNode.add(rootPomTreeNode);
		
		return configurationTreeNode;
	}

}
