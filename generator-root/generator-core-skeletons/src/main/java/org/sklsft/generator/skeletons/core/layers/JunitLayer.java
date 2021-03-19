package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToManyComponent;
import org.sklsft.generator.model.domain.business.OneToOneComponent;
import org.sklsft.generator.skeletons.core.commands.junit.BeanPopulatorFileTemplateCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.LogbackTestFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.SpringJUnitPersistenceConfigFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.SpringTestsConfigFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.TestsPomFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.configuration.TestsPropertiesFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.resources.JUnitDataInitializerFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.junit.resources.SetupTestFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.BeanPopulatorCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.OneToManyComponentPopulatorCommandFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.population.OneToOneComponentPopulatorCommandFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class JunitLayer extends AbstractLayer {
	
	public JunitLayer() {
		super("JUnit tests");
	}

	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode dataInitializerTreeNode = new FileWriteCommandTreeNode(new JUnitDataInitializerFileWriteCommand(project));
		resourcesTreeNode.add(dataInitializerTreeNode);
		
		FileWriteCommandTreeNode testSetupTreeNode = new FileWriteCommandTreeNode(new SetupTestFileWriteCommand(project));
		resourcesTreeNode.add(testSetupTreeNode);
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode testPomTreeNode = new FileWriteCommandTreeNode(new TestsPomFileWriteCommand(project));
		configurationTreeNode.add(testPomTreeNode);
		
		FileWriteCommandTreeNode springTestRepositoryTreeNode = new FileWriteCommandTreeNode(new SpringJUnitPersistenceConfigFileWriteCommand(project));
		configurationTreeNode.add(springTestRepositoryTreeNode);
		
		FileWriteCommandTreeNode springTestTreeNode = new FileWriteCommandTreeNode(new SpringTestsConfigFileWriteCommand(project));
		configurationTreeNode.add(springTestTreeNode);
		
		FileWriteCommandTreeNode logbackTestTreeNode = new FileWriteCommandTreeNode(new LogbackTestFileWriteCommand(project));
		configurationTreeNode.add(logbackTestTreeNode);
		
		FileWriteCommandTreeNode testPropertiesTreeNode = new FileWriteCommandTreeNode(new TestsPropertiesFileWriteCommand(project));
		configurationTreeNode.add(testPropertiesTreeNode);
		
		return configurationTreeNode;
	}
	
	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode dataCsvTemplates = new FileWriteCommandTreeNode();
        
        FileWriteCommandTreeNode commandTreeNode = new FileWriteCommandTreeNode("CSV templates");
        dataCsvTemplates.add(commandTreeNode);

        for (Package myPackage : project.model.packages)
        {
            FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
            commandTreeNode.add(packageTreeNode);

            for (Bean bean : myPackage.beans)
            {
                if (!bean.isComponent)
                {
                    FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new BeanPopulatorFileTemplateCommandFileWriteCommand(bean));
                    packageTreeNode.add(beanTreeNode);

                    for (OneToManyComponent oneToManyComponent : bean.oneToManyComponentList)
                    {
                        FileWriteCommandTreeNode oneToManyComponentTreeNode = new FileWriteCommandTreeNode(new OneToManyComponentPopulatorFileTemplateCommandFileWriteCommand(oneToManyComponent));
                        packageTreeNode.add(oneToManyComponentTreeNode);
                    }
                    
                    for (OneToOneComponent oneToOneComponent : bean.oneToOneComponentList)
                    {
                        FileWriteCommandTreeNode oneToOneComponentTreeNode = new FileWriteCommandTreeNode(new OneToOneComponentPopulatorFileTemplateCommandFileWriteCommand(oneToOneComponent));
                        packageTreeNode.add(oneToOneComponentTreeNode);
                    }
                }
            }
        }
        
        return dataCsvTemplates;
	}
}
