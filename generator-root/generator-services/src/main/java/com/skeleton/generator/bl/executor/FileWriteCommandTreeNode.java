package com.skeleton.generator.bl.executor;

import javax.swing.tree.DefaultMutableTreeNode;

import com.skeleton.generator.bl.command.file.interfaces.FileWriteCommand;

public class FileWriteCommandTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	
	transient protected FileWriteCommandExecutor userObject;

	public FileWriteCommandTreeNode(FileWriteCommand fileWriteCommand, String label) {
		super(new FileWriteCommandExecutor(fileWriteCommand, label));
	}
	
	public void execute() {
		userObject.execute();
		for (Object child:children) {
			((FileWriteCommandTreeNode)child).execute();
		}
	}
}
