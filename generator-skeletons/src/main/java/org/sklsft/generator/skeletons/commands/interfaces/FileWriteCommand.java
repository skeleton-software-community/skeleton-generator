package org.sklsft.generator.skeletons.commands.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

import org.sklsft.generator.skeletons.tree.FileWriteCommandExecutor;



/**
 * The code generator uses a tree of {@link FileWriteCommandExecutor}
 * each {@link FileWriteCommandExecutor} contains a FileWriteCommand to be executed
 * when executed, the command will write a file
 * @author Nicolas Thibault
 *
 */
public interface FileWriteCommand {

	void execute() throws IOException, URISyntaxException;
	
	String getLabel();
	
	int getRowCount();
}
