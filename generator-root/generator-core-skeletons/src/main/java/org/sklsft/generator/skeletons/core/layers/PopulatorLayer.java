package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.skeletons.core.commands.population.BeanPopulatorCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.OneToManyComponentPopulatorCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.configuration.LogbackPopulatorFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.configuration.PopulatorPomFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.configuration.PopulatorProjectPropertiesFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.configuration.SpringPopulatorFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.configuration.SpringPopulatorRepositoryFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.resources.PopulatorLauncherFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class PopulatorLayer extends AbstractLayer {
	
	public PopulatorLayer() {
		super("Population");
	}
	
	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode populatorLauncherTreeNode = new FileWriteCommandTreeNode(new PopulatorLauncherFileWriteCommand(project));
		resourcesTreeNode.add(populatorLauncherTreeNode);
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode populatorPomTreeNode = new FileWriteCommandTreeNode(new PopulatorPomFileWriteCommand(project));
		configurationTreeNode.add(populatorPomTreeNode);
		
		FileWriteCommandTreeNode springPopulatorRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorRepositoryFileWriteCommand(project));
		configurationTreeNode.add(springPopulatorRepositoryTreeNode);
		
		FileWriteCommandTreeNode springPopulatorTreeNode = new FileWriteCommandTreeNode(new SpringPopulatorFileWriteCommand(project));
		configurationTreeNode.add(springPopulatorTreeNode);
		
		FileWriteCommandTreeNode logbackPopulatorTreeNode = new FileWriteCommandTreeNode(new LogbackPopulatorFileWriteCommand(project));
		configurationTreeNode.add(logbackPopulatorTreeNode);
		
		FileWriteCommandTreeNode populatorPropertiesTreeNode = new FileWriteCommandTreeNode(new PopulatorProjectPropertiesFileWriteCommand(project));
		configurationTreeNode.add(populatorPropertiesTreeNode);
		
		return configurationTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode populatorLayerTreeNode = new FileWriteCommandTreeNode();
        
        FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode("Population commands");
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