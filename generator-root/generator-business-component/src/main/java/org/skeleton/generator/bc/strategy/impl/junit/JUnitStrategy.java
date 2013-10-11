package org.skeleton.generator.bc.strategy.impl.junit;

import org.skeleton.generator.bc.command.file.impl.java.junit.CommandBuilderFactoryFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewBeanBuilderFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewBeanCommandBuilderFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewBeanCommandFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentBuilderFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentCommandBuilderFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentCommandFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.services.BaseServiceImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.services.BaseServiceInterfaceFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.services.ServiceImplFileWriteCommand;
import org.skeleton.generator.bc.command.file.impl.java.services.ServiceInterfaceFileWriteCommand;
import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.bc.strategy.interfaces.LayerStrategy;
import org.skeleton.generator.model.om.Bean;
import org.skeleton.generator.model.om.OneToManyComponent;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;

public class JUnitStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode junitLayerTreeNode = new FileWriteCommandTreeNode("Services JUnit tests");

		FileWriteCommandTreeNode builderTreeNode = new FileWriteCommandTreeNode("View Objects Builders");
        junitLayerTreeNode.add(builderTreeNode);

        for (Package myPackage : project.model.packageList)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            builderTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beanList)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanBuilderFileWriteCommand(bean), bean.viewClassName + "Builder");
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentBuilderFileWriteCommand(oneToManyComponent), oneToManyComponent.referenceBean.viewClassName + "Builder");
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        
        FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode("View Objects commands");
        junitLayerTreeNode.add(commandTreeNode);

        for (Package myPackage : project.model.packageList)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            commandTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beanList)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanCommandFileWriteCommand(bean), bean.viewClassName + "Command");
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentCommandFileWriteCommand(oneToManyComponent), oneToManyComponent.referenceBean.viewClassName + "Command");
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        FileWriteCommandTreeNode commandBuilderTreeNode = new FileWriteCommandTreeNode("View Objects command builders");
        junitLayerTreeNode.add(commandBuilderTreeNode);

        for (Package myPackage : project.model.packageList)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            commandBuilderTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beanList)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanCommandBuilderFileWriteCommand(bean), bean.viewClassName + "CommandBuilder");
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentCommandBuilderFileWriteCommand(oneToManyComponent), oneToManyComponent.referenceBean.viewClassName + "CommandBuilder");
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        FileWriteCommandTreeNode commandBuilderFactoryTreeNode = new FileWriteCommandTreeNode(new CommandBuilderFactoryFileWriteCommand(project), "Command builder factory");
        junitLayerTreeNode.add(commandBuilderFactoryTreeNode);
        
        
        return junitLayerTreeNode;
	}
}