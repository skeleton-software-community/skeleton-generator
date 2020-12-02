package org.sklsft.generator.skeletons.rest.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.rest.commands.BaseRestClientFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.RestClientFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestClientConfigFileWriteCommand;
import org.sklsft.generator.skeletons.rest.commands.configuration.SpringRestClientPomFileWriteCommand;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class SpringRestClientLayer extends AbstractLayer {
	
	public SpringRestClientLayer() {
		super("Spring REST Clients");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
				
		FileWriteCommandTreeNode pomTreeNode = new FileWriteCommandTreeNode(new SpringRestClientPomFileWriteCommand(project));
		configurationTreeNode.add(pomTreeNode);
		
		FileWriteCommandTreeNode springContextTreeNode = new FileWriteCommandTreeNode(new SpringRestClientConfigFileWriteCommand(project));
		configurationTreeNode.add(springContextTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode mainTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode baseRestClientTreeNode = new FileWriteCommandTreeNode("Base REST clients");
		mainTreeNode.add(baseRestClientTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseRestClientTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanBaseRestClientTreeNode = new FileWriteCommandTreeNode(new BaseRestClientFileWriteCommand(bean));
					packageTreeNode.add(beanBaseRestClientTreeNode);
				}
			}
		}
		
		FileWriteCommandTreeNode controllerTreeNode = new FileWriteCommandTreeNode("REST clients");
		mainTreeNode.add(controllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			controllerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanRestClientTreeNode = new FileWriteCommandTreeNode(new RestClientFileWriteCommand(bean));
					packageTreeNode.add(beanRestClientTreeNode);
				}
			}
		}
		
		return mainTreeNode;
	}

}