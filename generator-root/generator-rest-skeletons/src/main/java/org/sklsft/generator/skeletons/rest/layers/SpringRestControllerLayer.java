package org.sklsft.generator.skeletons.rest.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.rest.commands.SpringRestBaseControllerCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestLogbackFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestProjectPropertiesFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestRootPomFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestSpringContextFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestWarPomFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestWebXmlFileWriteCommand;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class SpringRestControllerLayer extends AbstractLayer {
	
	public SpringRestControllerLayer() {
		super("Spring REST Controllers");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode propertiesTreeNode = new FileWriteCommandTreeNode(new SpringRestProjectPropertiesFileWriteCommand(project));
		configurationTreeNode.add(propertiesTreeNode);
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new SpringRestLogbackFileWriteCommand(project));
		configurationTreeNode.add(logbackTreeNode);
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new SpringRestRootPomFileWriteCommand(project));
		configurationTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new SpringRestWarPomFileWriteCommand(project));
		configurationTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new SpringRestWebXmlFileWriteCommand(project));
		configurationTreeNode.add(webXmlPomTreeNode);
				
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new SpringRestSpringContextFileWriteCommand(project));
		configurationTreeNode.add(springWebappTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode controllersLayerTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode("REST Base Controllers");
		controllersLayerTreeNode.add(baseControllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseControllerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanBaseControllerTreeNode = new FileWriteCommandTreeNode(new SpringRestBaseControllerCommand(bean));
					packageTreeNode.add(beanBaseControllerTreeNode);
				}
			}
		}
		
		return controllersLayerTreeNode;
	}

}