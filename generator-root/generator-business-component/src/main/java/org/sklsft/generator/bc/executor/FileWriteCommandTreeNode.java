package org.sklsft.generator.bc.executor;

import javax.swing.tree.DefaultMutableTreeNode;

import org.sklsft.generator.bc.command.file.interfaces.FileWriteCommand;

/**
 * A node of the {@link FileWriteCommandTree} which contains a {@link FileWriteCommandExecutor}
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
	
	public void execute() {
		((FileWriteCommandExecutor)userObject).execute();
		if (children != null) {
			for (Object child:children) {
				((FileWriteCommandTreeNode)child).execute();
			}
		}
	}
}
