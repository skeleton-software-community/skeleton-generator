package org.sklsft.generator.bl.services.impl;

import org.sklsft.generator.bc.resolvers.SkeletonResolver;
import org.sklsft.generator.bl.services.interfaces.CodeGenerator;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.Skeleton;
import org.sklsft.generator.skeletons.layers.Layer;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class CodeGeneratorImpl implements CodeGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorImpl.class);
	
	
	@Override
	public void initResources(Project project) {

		Skeleton skeleton = SkeletonResolver.getSkeleton(project);
		
		for (Layer layer : skeleton.getLayers()) {			
			FileWriteCommandTreeNode root = layer.getResourcesNode(project);
			if (root != null) {
				logger.info("start copying resources for layer : " + layer.getName());
				
				root.execute();
			}
		}		
	}

	
	@Override
	public void initConfiguration(Project project) {

		Skeleton skeleton = SkeletonResolver.getSkeleton(project);
		
		for (Layer layer : skeleton.getLayers()) {			
			FileWriteCommandTreeNode root = layer.getConfigurationNode(project);
			if (root != null) {
				logger.info("start creating configuration for layer : " + layer.getName());
				
				root.execute();
			}
		}
	}


	@Override
	public void generateCode(Project project) {

		Skeleton skeleton = SkeletonResolver.getSkeleton(project);
		
		for (Layer layer : skeleton.getLayers()) {			
			FileWriteCommandTreeNode root = layer.getGenerationNode(project);
			if (root != null) {
				logger.info("start generating layer : " + layer.getName());
				
				root.execute();
			}
		}		
	}
}
