package org.sklsft.generator.skeletons.jsf.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.commands.impl.ResourcesFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.I18nFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesDetailMenuFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesDetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesOneToManyComponentListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesOneToManyComponentModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesOneToManyListViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesOneToManyModalViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.PrimefacesOneToOneComponentDetailViewFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration.PrimefacesRootPomFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration.PrimefacesSpringWebappFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration.PrimefacesWebXmlFileWriteCommand;
import org.sklsft.generator.skeletons.jsf.commands.presentation.primefaces.configuration.PrimefacesWebappPomFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class PrimefacesPresentationLayer extends AbstractLayer {
	
	public PrimefacesPresentationLayer() {
		super("MVC Primefaces presentation");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode copyResourcesTreeNode = new FileWriteCommandTreeNode(new ResourcesFileWriteCommand(project, getClass(), "/primefaces/src/",project.projectName + "-webapp"));
		resourcesTreeNode.add(copyResourcesTreeNode);
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode rootPomTreeNode = new FileWriteCommandTreeNode(new PrimefacesRootPomFileWriteCommand(project));
		configurationTreeNode.add(rootPomTreeNode);
		
		FileWriteCommandTreeNode webappPomTreeNode = new FileWriteCommandTreeNode(new PrimefacesWebappPomFileWriteCommand(project));
		configurationTreeNode.add(webappPomTreeNode);
		
		FileWriteCommandTreeNode webXmlPomTreeNode = new FileWriteCommandTreeNode(new PrimefacesWebXmlFileWriteCommand(project));
		configurationTreeNode.add(webXmlPomTreeNode);
				
		FileWriteCommandTreeNode springWebappTreeNode = new FileWriteCommandTreeNode(new PrimefacesSpringWebappFileWriteCommand(project));
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
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new PrimefacesListViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					FileWriteCommandTreeNode beanCreationTreeNode = new FileWriteCommandTreeNode(new PrimefacesModalViewFileWriteCommand(bean));
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
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new PrimefacesDetailViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					FileWriteCommandTreeNode menuTreeNode = new FileWriteCommandTreeNode(new PrimefacesDetailMenuFileWriteCommand(bean));
					packageTreeNode.add(menuTreeNode);
					
					for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new PrimefacesOneToManyComponentListViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentListTreeNode);
																		
						FileWriteCommandTreeNode componentModalTreeNode = new FileWriteCommandTreeNode(new PrimefacesOneToManyComponentModalViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentModalTreeNode);
					}
					
					for (OneToMany oneToMany:bean.oneToManyList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new PrimefacesOneToManyListViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentListTreeNode);
						
						FileWriteCommandTreeNode componentCreationTreeNode = new FileWriteCommandTreeNode(new PrimefacesOneToManyModalViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentCreationTreeNode);
					}
					
					for (OneToOneComponent uniqueComponent:bean.oneToOneComponentList) {
						FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new PrimefacesOneToOneComponentDetailViewFileWriteCommand(uniqueComponent));
						packageTreeNode.add(componentTreeNode);
					}
				}
			}
		}
		
		return presentationLayerTreeNode;
	}

}
