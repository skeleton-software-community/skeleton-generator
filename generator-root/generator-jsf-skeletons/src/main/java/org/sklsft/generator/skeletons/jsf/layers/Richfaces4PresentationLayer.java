package org.sklsft.generator.skeletons.jsf.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.ResourcesFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.I18nFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4DetailMenuFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4DetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4ListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4ModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4OneToManyComponentListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4OneToManyComponentModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4OneToManyListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4OneToManyModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.Richfaces4OneToOneComponentDetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.configuration.Richfaces4RootPomFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.configuration.Richfaces4SpringWebappFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.configuration.Richfaces4WebXmlFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.richfaces4.configuration.Richfaces4WebappPomFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class Richfaces4PresentationLayer extends AbstractLayer {
	
	public Richfaces4PresentationLayer() {
		super("MVC Richfaces 4 presentation");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode copyResourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, getClass(), "/richfaces4/src/",project.projectName + "-webapp"));
		resourcesTreeNode.add(copyResourcesTreeNode);
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4RootPomFileWriteCommand(project));
		configurationTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4WebappPomFileWriteCommand(project));
		configurationTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new Richfaces4WebXmlFileWriteCommand(project));
		configurationTreeNode.add(webXmlPomTreeNode);
				
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new Richfaces4SpringWebappFileWriteCommand(project));
		configurationTreeNode.add(springWebappTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {
		
		FileWriteCommandTreeNode presentationLayerTreeNode = new FileWriteCommandTreeNode();

		FileWriteCommandTreeNode i18nTreeNode = new FileWriteCommandTreeNode(new I18nFileWriteCommand(project));
		presentationLayerTreeNode.add(i18nTreeNode);
		
		FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode("List view files");
		presentationLayerTreeNode.add(listViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			listViewTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new Richfaces4ListViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					FileWriteCommandTreeNode beanCreationTreeNode = new FileWriteCommandTreeNode(new Richfaces4ModalViewFileWriteCommand(bean));
					packageTreeNode.add(beanCreationTreeNode);
				}
			}
		}

		FileWriteCommandTreeNode detailViewTreeNode = new FileWriteCommandTreeNode("Detail view files");
		presentationLayerTreeNode.add(detailViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			detailViewTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new Richfaces4DetailViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					FileWriteCommandTreeNode menuTreeNode = new FileWriteCommandTreeNode(new Richfaces4DetailMenuFileWriteCommand(bean));
					packageTreeNode.add(menuTreeNode);
					
					for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new Richfaces4OneToManyComponentListViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentListTreeNode);
																		
						FileWriteCommandTreeNode componentModalTreeNode = new FileWriteCommandTreeNode(new Richfaces4OneToManyComponentModalViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentModalTreeNode);
					}
					
					for (OneToMany oneToMany:bean.oneToManyList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new Richfaces4OneToManyListViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentListTreeNode);
						
						FileWriteCommandTreeNode componentCreationTreeNode = new FileWriteCommandTreeNode(new Richfaces4OneToManyModalViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentCreationTreeNode);
					}
					
					for (OneToOneComponent uniqueComponent:bean.oneToOneComponentList) {
						FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new Richfaces4OneToOneComponentDetailViewFileWriteCommand(uniqueComponent));
						packageTreeNode.add(componentTreeNode);
					}
				}
			}
		}
		
		return presentationLayerTreeNode;
	}

}
