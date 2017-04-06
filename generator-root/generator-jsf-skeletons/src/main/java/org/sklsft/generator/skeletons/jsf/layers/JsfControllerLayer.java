package org.sklsft.generator.skeletons.jsf.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.jsf.commands.controller.JsfBaseDetailControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.JsfBaseListControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.JsfCommonControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.JsfDetailControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.JsfListControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.configuration.LogbackFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.configuration.MavenInstallBatchFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.configuration.ProjectPropertiesFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.controller.resources.JsfBaseControllerFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.model.JsfCommonViewFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class JsfControllerLayer extends AbstractLayer {
	
	public JsfControllerLayer() {
		super("MVC Controller");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode(new JsfBaseControllerFileWriteCommand(project));
		resourcesTreeNode.add(baseControllerTreeNode);
		
		return resourcesTreeNode;		
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode propertiesTreeNode = new FileWriteCommandTreeNode(new ProjectPropertiesFileWriteCommand(project));
		configurationTreeNode.add(propertiesTreeNode);
		
		FileWriteCommandTreeNode logbackTreeNode = new FileWriteCommandTreeNode(new LogbackFileWriteCommand(project));
		configurationTreeNode.add(logbackTreeNode);
		
		FileWriteCommandTreeNode mavenInstallPomTreeNode = new FileWriteCommandTreeNode(new MavenInstallBatchFileWriteCommand(project));
		configurationTreeNode.add(mavenInstallPomTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode mvcLayerTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode commonControllerTreeNode = new FileWriteCommandTreeNode(new JsfCommonControllerFileWriteCommand(project));
		mvcLayerTreeNode.add(commonControllerTreeNode);
		
		FileWriteCommandTreeNode commonViewTreeNode = new FileWriteCommandTreeNode(new JsfCommonViewFileWriteCommand(project));
		mvcLayerTreeNode.add(commonViewTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode("Base Controllers");
		mvcLayerTreeNode.add(baseControllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseControllerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode listBeanTreeNode = new FileWriteCommandTreeNode(new JsfBaseListControllerFileWriteCommand(bean));
					packageTreeNode.add(listBeanTreeNode);
					
					FileWriteCommandTreeNode detailBeanTreeNode = new FileWriteCommandTreeNode(new JsfBaseDetailControllerFileWriteCommand(bean));
					packageTreeNode.add(detailBeanTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode controllerTreeNode = new FileWriteCommandTreeNode("Controllers");
		mvcLayerTreeNode.add(controllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			controllerTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode listBeanTreeNode = new FileWriteCommandTreeNode(new JsfListControllerFileWriteCommand(bean));
					controllerTreeNode.add(listBeanTreeNode);
					
					FileWriteCommandTreeNode detailBeanTreeNode = new FileWriteCommandTreeNode(new JsfDetailControllerFileWriteCommand(bean));
					controllerTreeNode.add(detailBeanTreeNode);
				}
			}
		}
		
		return mvcLayerTreeNode;
	}

}
