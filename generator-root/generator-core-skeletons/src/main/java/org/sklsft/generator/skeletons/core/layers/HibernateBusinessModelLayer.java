package org.sklsft.generator.skeletons.core.layers;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.skeletons.core.commands.model.EntityBeanFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.model.configuration.BusinessModelPomFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.model.resources.AuditEntityFileWriteCommand;
import org.sklsft.generator.skeletons.core.commands.model.resources.AuditListenerFileWriteCommand;
import org.sklsft.generator.skeletons.layers.AbstractLayer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

public class HibernateBusinessModelLayer extends AbstractLayer {
	
	public HibernateBusinessModelLayer() {
		super("Business Model / Hibernate mappings");
	}

	@Override
	public FileWriteCommandTreeNode getResourcesNode(Project project) {
		
		FileWriteCommandTreeNode resourcesTreeNode = new FileWriteCommandTreeNode();
		
		if (project.audited) {
			FileWriteCommandTreeNode auditEntityTreeNode = new FileWriteCommandTreeNode(new AuditEntityFileWriteCommand(project));
			resourcesTreeNode.add(auditEntityTreeNode);
		
			FileWriteCommandTreeNode auditListenerTreeNode = new FileWriteCommandTreeNode(new AuditListenerFileWriteCommand(project));
			resourcesTreeNode.add(auditListenerTreeNode);
		}
		
		return resourcesTreeNode;
	}

	@Override
	public FileWriteCommandTreeNode getConfigurationNode(Project project) {
		
		FileWriteCommandTreeNode configurationTreeNode = new FileWriteCommandTreeNode();
		
		FileWriteCommandTreeNode businessModelPomTreeNode = new FileWriteCommandTreeNode(new BusinessModelPomFileWriteCommand(project));
		configurationTreeNode.add(businessModelPomTreeNode);
		
		return configurationTreeNode;
	}
	
	@Override
	public FileWriteCommandTreeNode getGenerationNode(Project project) {

		FileWriteCommandTreeNode businessModelTreeNode = new FileWriteCommandTreeNode();

		for (Package myPackage : project.model.packages) {
			FileWriteCommandTreeNode packageTreeNode = new FileWriteCommandTreeNode(myPackage.name);
			businessModelTreeNode.add(packageTreeNode);

			FileWriteCommandTreeNode omTreeNode = new FileWriteCommandTreeNode("Entities");
			packageTreeNode.add(omTreeNode);
			for (Bean bean : myPackage.beans) {
				FileWriteCommandTreeNode beanTreeNode = new FileWriteCommandTreeNode(new EntityBeanFileWriteCommand(bean));
				omTreeNode.add(beanTreeNode);
			}
		}

		return businessModelTreeNode;
	}
}
