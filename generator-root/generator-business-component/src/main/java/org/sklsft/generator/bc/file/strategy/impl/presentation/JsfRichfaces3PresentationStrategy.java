package org.sklsft.generator.bc.file.strategy.impl.presentation;

import org.sklsft.generator.bc.file.command.impl.presentation.jsf.I18nFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfModalViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyComponentListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyComponentModalViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyModalViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToOneComponentDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;

public class JsfRichfaces3PresentationStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode presentationLayerTreeNode = new FileWriteCommandTreeNode("Presentation Layer");

		FileWriteCommandTreeNode i18nTreeNode = new FileWriteCommandTreeNode(new I18nFileWriteCommand(project));
		presentationLayerTreeNode.add(i18nTreeNode);
		
		FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode("List view files");
		presentationLayerTreeNode.add(listViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			listViewTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new JsfListViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					FileWriteCommandTreeNode beanCreationTreeNode = new FileWriteCommandTreeNode(new JsfModalViewFileWriteCommand(bean));
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
					
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new JsfDetailViewFileWriteCommand(bean));
					packageTreeNode.add(beanTreeNode);
					
					for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentListViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentListTreeNode);
						
						FileWriteCommandTreeNode componentModalTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentModalViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentModalTreeNode);
					}
					
					for (OneToMany oneToMany:bean.oneToManyList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyListViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentListTreeNode);
						
						FileWriteCommandTreeNode componentModalTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyModalViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentModalTreeNode);
					}
					
					for (OneToOneComponent oneToOneComponent:bean.oneToOneComponentList) {
						FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new JsfOneToOneComponentDetailViewFileWriteCommand(oneToOneComponent));
						packageTreeNode.add(componentTreeNode);
					}
				}
			}
		}
		
		return presentationLayerTreeNode;
	}

}
