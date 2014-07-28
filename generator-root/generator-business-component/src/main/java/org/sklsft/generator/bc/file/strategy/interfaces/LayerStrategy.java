package org.sklsft.generator.bc.file.strategy.interfaces;

import org.sklsft.generator.bc.file.executor.FileWriteCommandTreeNode;
import org.sklsft.generator.model.om.Project;

public interface LayerStrategy {

	FileWriteCommandTreeNode getLayerNode(Project project);
}
