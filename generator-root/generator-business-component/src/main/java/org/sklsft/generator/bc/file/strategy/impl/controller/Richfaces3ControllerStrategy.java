package org.sklsft.generator.bc.file.strategy.impl.controller;

import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.JsfCommonControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.JsfDetailControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.JsfListControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.richfaces3.BaseJsfRichfaces3DetailControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.controller.richfaces.richfaces3.BaseJsfRichfaces3ListControllerFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.JsfCommonViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.MvcListViewFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.mvc.model.richfaces.richfaces3.MvcDetailViewFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;

public class Richfaces3ControllerStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode mvcLayerTreeNode = new FileWriteCommandTreeNode("Mvc Layer");

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
					FileWriteCommandTreeNode listBeanTreeNode = new FileWriteCommandTreeNode(new BaseJsfRichfaces3ListControllerFileWriteCommand(bean));
					packageTreeNode.add(listBeanTreeNode);
					
					FileWriteCommandTreeNode detailBeanTreeNode = new FileWriteCommandTreeNode(new BaseJsfRichfaces3DetailControllerFileWriteCommand(bean));
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
		
		FileWriteCommandTreeNode listViewTreeNode = new FileWriteCommandTreeNode("List view");
		mvcLayerTreeNode.add(listViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			listViewTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MvcListViewFileWriteCommand(bean));
					listViewTreeNode.add(beanTreeNode);
				}
			}
		}
		
		FileWriteCommandTreeNode detailViewTreeNode = new FileWriteCommandTreeNode("Detail view");
		mvcLayerTreeNode.add(detailViewTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			detailViewTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new MvcDetailViewFileWriteCommand(bean));
					detailViewTreeNode.add(beanTreeNode);
				}
			}
		}
		
		return mvcLayerTreeNode;
	}

}
