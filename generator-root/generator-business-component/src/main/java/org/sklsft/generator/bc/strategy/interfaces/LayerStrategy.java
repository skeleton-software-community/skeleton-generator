package org.sklsft.generator.bc.strategy.interfaces;

import org.sklsft.generator.bc.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.model.om.Project;

public interface LayerStrategy {

	FileWriteCommandTreeNode getLayerNode(Project project);
}
