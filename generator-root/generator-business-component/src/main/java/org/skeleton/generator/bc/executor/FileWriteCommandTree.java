package org.skeleton.generator.bc.executor;

import javax.swing.JTree;

public class FileWriteCommandTree extends JTree {

	private static final long serialVersionUID = 1L;

	public FileWriteCommandTree(FileWriteCommandTreeNode fileWriteCommandTreeNode) {
		super(fileWriteCommandTreeNode);
	}

	public void launch() {
		((FileWriteCommandTreeNode)getModel().getRoot()).execute();
	}
}
