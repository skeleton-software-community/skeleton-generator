package org.sklsft.generator.bc.file.strategy.impl.population;

import org.sklsft.generator.bc.file.command.impl.java.population.CommandFactoryFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewBeanBuilderFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewBeanCommandFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewOneToManyComponentBuilderFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewOneToManyComponentCommandFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.om.Bean;
import org.sklsft.generator.model.om.OneToManyComponent;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;

public class PopulatorStrategy implements LayerStrategy {

	@Override
	public FileWriteCommandTreeNode getLayerNode(Project project) {

		FileWriteCommandTreeNode populatorLayerTreeNode = new FileWriteCommandTreeNode("Populator");

		FileWriteCommandTreeNode builderTreeNode = new FileWriteCommandTreeNode("View Objects Builders");
        populatorLayerTreeNode.add(builderTreeNode);

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
        populatorLayerTreeNode.add(commandTreeNode);

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
        populatorLayerTreeNode.add(commandBuilderTreeNode);
        
        FileWriteCommandTreeNode commandBuilderFactoryTreeNode = new FileWriteCommandTreeNode(new CommandFactoryFileWriteCommand(project));
        populatorLayerTreeNode.add(commandBuilderFactoryTreeNode);
        
        
        return populatorLayerTreeNode;
	}
}