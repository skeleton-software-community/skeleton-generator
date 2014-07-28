package org.sklsft.generator.bc.file.executor;

import javax.swing.JTree;

import org.sklsft.generator.bc.file.strategy.interfaces.LayerStrategy;

/**
 * This class represents a tree of {@link FileWriteCommandTreeNode} wich will be cascade launched<br/>
 * Launching a code generation will build a tree that represents the skeleton of your application<br/>
 * each tree has several layer nodes given by a {@link LayerStrategy}
 * each layer node will consists of several files to be written
 * 
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandTree extends JTree {

	private static final long serialVersionUID = 1L;

	public FileWriteCommandTree(FileWriteCommandTreeNode fileWriteCommandTreeNode) {
		super(fileWriteCommandTreeNode);
	}

	public void launch() {
		((FileWriteCommandTreeNode)getModel().getRoot()).execute();
	}
}
