package org.sklsft.generator.bc.file.strategy.impl.presentation;

import org.sklsft.generator.bc.file.command.impl.presentation.jsf.I18nFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfCreationViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyComponentCreationViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyComponentDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyComponentListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyCreationViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfOneToManyListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.presentation.jsf.richfaces3.JsfUniqueComponentDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToMany;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.UniqueComponent;

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
					
					FileWriteCommandTreeNode beanCreationTreeNode = new FileWriteCommandTreeNode(new JsfCreationViewFileWriteCommand(bean));
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
						
						FileWriteCommandTreeNode componentDetailTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentDetailViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentDetailTreeNode);
						
						FileWriteCommandTreeNode componentCreationTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentCreationViewFileWriteCommand(oneToManyComponent));
						packageTreeNode.add(componentCreationTreeNode);
					}
					
					for (OneToMany oneToMany:bean.oneToManyList) {
						
						FileWriteCommandTreeNode componentListTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyListViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentListTreeNode);
						
						FileWriteCommandTreeNode componentCreationTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyCreationViewFileWriteCommand(oneToMany));
						packageTreeNode.add(componentCreationTreeNode);
					}
					
					for (UniqueComponent uniqueComponent:bean.uniqueComponentList) {
						FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new JsfUniqueComponentDetailViewFileWriteCommand(uniqueComponent));
						packageTreeNode.add(componentTreeNode);
					}
				}
			}
		}
		
		return presentationLayerTreeNode;
	}

}
