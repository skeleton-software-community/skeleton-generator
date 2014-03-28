package org.skeleton.generator.bc.executor;

import javax.swing.tree.DefaultMutableTreeNode;

import org.skeleton.generator.bc.command.file.interfaces.FileWriteCommand;

/**
 * A node of the {@link FileWriteCommandTree}
 * @author Nicolas Thibault
 *
 */
public class FileWriteCommandTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	public FileWriteCommandTreeNode(FileWriteCommand fileWriteCommand, String label) {
		super(new FileWriteCommandExecutor(fileWriteCommand, label));
	}
	
	public FileWriteCommandTreeNode(String label) {
		super(new FileWriteCommandExecutor(null, label));
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
