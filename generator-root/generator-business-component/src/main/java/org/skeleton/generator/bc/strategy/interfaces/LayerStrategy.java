package org.skeleton.generator.bc.strategy.interfaces;

import org.skeleton.generator.bc.executor.FileWriteCommandTreeNode;
import org.skeleton.generator.model.om.Project;

public interface LayerStrategy {

	FileWriteCommandTreeNode getLayerNode(Project project);
}
