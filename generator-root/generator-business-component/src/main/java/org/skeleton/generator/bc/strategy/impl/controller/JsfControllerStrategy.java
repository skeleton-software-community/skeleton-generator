package org.skeleton.generator.bc.strategy.impl.controller;

import org.skeleton.generator.bc.command.file.impl.java.controller.jsf.BaseJsfControllerFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.controller.jsf.BaseSimpleJsfControllerFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.controller.jsf.DataTableFilterFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.controller.jsf.JsfCommonControllerFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.controller.jsf.JsfControllerFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;

public class JsfControllerStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {
		
		FileWriteCommandTreeNode controllerLayerTreeNode = new FileWriteCommandTreeNode("Controllers Layer");

		FileWriteCommandTreeNode commonControllerTreeNode = new FileWriteCommandTreeNode(new JsfCommonControllerFileWriteCommand(project));
		controllerLayerTreeNode.add(commonControllerTreeNode);
		
		FileWriteCommandTreeNode baseControllerTreeNode = new FileWriteCommandTreeNode("Base Controllers");
		controllerLayerTreeNode.add(baseControllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			baseControllerTreeNode.add(packageTreeNode);
			
			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					if (bean.isSimple()) {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseSimpleJsfControllerFileWriteCommand(bean));
						packageTreeNode.add(beanTreeNode);
					} else {
						FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BaseJsfControllerFileWriteCommand(bean));
						packageTreeNode.add(beanTreeNode);
					}
				}
			}
		}

		FileWriteCommandTreeNode controllerTreeNode = new FileWriteCommandTreeNode("Controllers");
		controllerLayerTreeNode.add(controllerTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			controllerTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				if (!bean.isComponent) {
					FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new JsfControllerFileWriteCommand(bean));
					controllerTreeNode.add(beanTreeNode);
				}
			}
		}
		
		FileWriteCommandTreeNode filterTreeNode = new FileWriteCommandTreeNode("DataTable filters");
		controllerLayerTreeNode.add(filterTreeNode);

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			filterTreeNode.add(packageTreeNode);

			for (Bean bean : myPackage.beans) {
				
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new DataTableFilterFileWriteCommand(bean));
				filterTreeNode.add(beanTreeNode);

			}
		}
		
		return controllerLayerTreeNode;
	}

}
