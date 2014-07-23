package org.sklsft.generator.bc.command.file.interfaces;

import java.io.IOException;

import org.sklsft.generator.bc.executor.FileWriteCommandExecutor;


/**
 * The code generator uses a tree of {@link FileWriteCommandExecutor}
 * each {@link FileWriteCommandExecutor} contains a FileWriteCommand to be executed
 * when executed, the command will write a file
 * @author Nicolas Thibault
 *
 */
public interface FileWriteCommand {

	void execute() throws IOException;
	
	String getLabel();
	
	int getRowCount();
}
