package org.sklsft.generator.bc.file.strategy.impl.population;

import org.sklsft.generator.bc.file.command.impl.java.population.BeanPopulatorCommandFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.OneToManyComponentPopulatorCommandFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewBeanBuilderFileWriteCommand;
import org.sklsft.generator.bc.file.command.impl.java.population.ViewOneToManyComponentBuilderFileWriteCommand;
import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;

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
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BeanPopulatorCommandFileWriteCommand(bean));
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new OneToManyComponentPopulatorCommandFileWriteCommand(oneToManyComponent));
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                }
            }
        }
        
        return populatorLayerTreeNode;
	}
}