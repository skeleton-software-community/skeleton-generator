package org.sklsft.generator.skeletons.layers;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.tree.FileWriteCommandTreeNode;

/**
 * An Application Skeleton is divided into several Layers which takes care of a particular role<br>
 * Each Layer can have resources, configuration files and generated files
 * @author Nicolas Thibault
 *
 */
public interface Layer {

	/**
	 * get the displayed name of the layer
	 * @return
	 */
	String getName();
	
	/**
	 * get the resources to be copied when initializing a {@Link Project}
	 * @param project
	 * @return
	 */
	FileWriteCommandTreeNode getResourcesNode(Project project);
	
	/**
	 * get the configuration files to be written when initializing a {@Link Project}
	 * @param project
	 * @return
	 */
	FileWriteCommandTreeNode getConfigurationNode(Project project);
	
	/**
	 * get the files to be written when generating code for a {@Link Project}
	 * @param project
	 * @return
	 */
	FileWriteCommandTreeNode getGenerationNode(Project project);
}
