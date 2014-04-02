package org.skeleton.generator.bc.strategy.impl.configuration;

import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesBusinessComponentPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesBusinessModelPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesRepositoryPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesRootPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesServicesPomFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.conf.pom.SpringHibernateRichfacesUtilPomFileWriteCommand;
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
		
		FileWriteCommandTreeNode utilPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesUtilPomFileWriteCommand(project),"util pom.xml");
		pomTreeNode.add(utilPomTreeNode);
		
		FileWriteCommandTreeNode businessModelPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessModelPomFileWriteCommand(project),"business model pom.xml");
		pomTreeNode.add(businessModelPomTreeNode);
		
		FileWriteCommandTreeNode repositoryPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesRepositoryPomFileWriteCommand(project),"repository pom.xml");
		pomTreeNode.add(repositoryPomTreeNode);
		
		FileWriteCommandTreeNode businessComponentPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesBusinessComponentPomFileWriteCommand(project),"business component pom.xml");
		pomTreeNode.add(businessComponentPomTreeNode);
		
		FileWriteCommandTreeNode servicesPomTreeNode = new FileWriteCommandTreeNode(new SpringHibernateRichfacesServicesPomFileWriteCommand(project),"services pom.xml");
		pomTreeNode.add(servicesPomTreeNode);
		
		return configurationTreeNode;
	}

}
