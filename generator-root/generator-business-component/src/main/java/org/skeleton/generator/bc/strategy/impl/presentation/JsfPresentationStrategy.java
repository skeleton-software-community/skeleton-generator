package org.skeleton.generator.bc.strategy.impl.presentation;

import org.skeleton.generator.bc.command.file.impl.presentation.jsf.I18nFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.JsfFacesConfigFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.JsfDetailViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.JsfListViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.JsfOneToManyComponentDetailViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.JsfOneToManyComponentListViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.JsfUniqueComponentDetailViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.SimpleJsfDetailViewFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.presentation.jsf.complete.SimpleJsfListViewFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.UniqueComponent;

public class JsfPresentationStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode presentationLayerTreeNode = new FileWriteCommandTreeNode("Presentation Layer");

		FileWriteCommandTreeNode i18nTreeNode = new FileWriteCommandTreeNode(new I18nFileWriteCommand(project), "I18n file");
		presentationLayerTreeNode.add(i18nTreeNode);
		
		FileWriteCommandTreeNode facesConfigTreeNode = new FileWriteCommandTreeNode(new JsfFacesConfigFileWriteCommand(project), "Faces Config");
		presentationLayerTreeNode.add(facesConfigTreeNode);
		
		FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode("List view files");
		presentationLayerTreeNode.add(listViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			listViewTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					if (bean.isSimple()) {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new SimpleJsfListViewFileWriteCommand(bean), bean.className + "List");
						packageTreeNode.add(beanTreeNode);
					} else {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new JsfListViewFileWriteCommand(bean), bean.className + "List");
						packageTreeNode.add(beanTreeNode);
						
						for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
							FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentListViewFileWriteCommand(oneToManyComponent), oneToManyComponent.referenceBean.className + "List");
							packageTreeNode.add(componentTreeNode);
						}
					}
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
					if (bean.isSimple()) {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new SimpleJsfDetailViewFileWriteCommand(bean), bean.className + "Detail");
						packageTreeNode.add(beanTreeNode);
					} else {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new JsfDetailViewFileWriteCommand(bean), bean.className + "Detail");
						packageTreeNode.add(beanTreeNode);
						
						for (OneToManyComponent oneToManyComponent:bean.oneToManyComponentList) {
							FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new JsfOneToManyComponentDetailViewFileWriteCommand(oneToManyComponent), oneToManyComponent.referenceBean.className + "Detail");
							packageTreeNode.add(componentTreeNode);
						}
						
						for (UniqueComponent uniqueComponent:bean.uniqueComponentList) {
							FileWriteCommandTreeNode componentTreeNode = new FileWriteCommandTreeNode(new JsfUniqueComponentDetailViewFileWriteCommand(uniqueComponent), uniqueComponent.referenceBean.className + "Detail");
							packageTreeNode.add(componentTreeNode);
						}
					}
				}
			}
		}
		
		return presentationLayerTreeNode;
	}

}
