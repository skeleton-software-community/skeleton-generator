package org.sklsft.generator.skeletons.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import org.sklsft.generator.skeletons.commands.interfaces.FileWriteCommand;
import org.sklsft.generator.skeletons.layers.Layer;

/**
 * A {@link Layer} will consists of several trees of FileWriteCommandTreeNode
 * Each node can have children
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	public FileWriteCommandTreeNode(FileWriteCommand fileWriteCommand) {
		super(new FileWriteCommandExecutor(fileWriteCommand));
	}
	
	public FileWriteCommandTreeNode(String label) {
		super(new FileWriteCommandExecutor(label));
	}
	
	public FileWriteCommandTreeNode() {
		super(new FileWriteCommandExecutor());
	}
	
	public void execute() {
		((FileWriteCommandExecutor)userObject).execute();
		if (children != null) {
			for (Object child:children) {
				((FileWriteCommandTreeNode)child).execute();
			}
		}
	}
}
