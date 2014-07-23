package org.sklsft.generator.bc.strategy.impl.junit;

import org.sklsft.generator.bc.command.file.impl.java.junit.CommandBuilderFactoryImplFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewBeanBuilderFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewBeanCommandBuilderFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewBeanCommandFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentBuilderFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentCommandBuilderFileWriteCommand;
import org.sklsft.generator.bc.command.file.impl.java.junit.ViewOneToManyComponentCommandFileWriteCommand;
import org.sklsft.generator.bc.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

public class JUnitStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode junitLayerTreeNode = new FileWriteCommandTreeNode("Services JUnit tests");

		FileWriteCommandTreeNode builderTreeNode = new FileWriteCommandTreeNode("View Objects Builders");
        junitLayerTreeNode.add(builderTreeNode);

        for (Package myPackage : project.model.packages)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            builderTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanBuilderFileWriteCommand(bean));
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentBuilderFileWriteCommand(oneToManyComponent));
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        
        FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode("View Objects commands");
        junitLayerTreeNode.add(commandTreeNode);

        for (Package myPackage : project.model.packages)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            commandTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanCommandFileWriteCommand(bean));
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentCommandFileWriteCommand(oneToManyComponent));
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        FileWriteCommandTreeNode commandBuilderTreeNode = new FileWriteCommandTreeNode("View Objects command builders");
        junitLayerTreeNode.add(commandBuilderTreeNode);

        for (Package myPackage : project.model.packages)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            commandBuilderTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new ViewBeanCommandBuilderFileWriteCommand(bean));
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new ViewOneToManyComponentCommandBuilderFileWriteCommand(oneToManyComponent));
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        FileWriteCommandTreeNode commandBuilderFactoryTreeNode = new FileWriteCommandTreeNode(new CommandBuilderFactoryImplFileWriteCommand(project));
        junitLayerTreeNode.add(commandBuilderFactoryTreeNode);
        
        
        return junitLayerTreeNode;
	}
}