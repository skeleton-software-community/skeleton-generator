package org.skeleton.generator.bc.command.file.impl.templatized;

import java.io.IOException;

import org.apache.velocity.Template;
import org.skeleton.generator.bc.command.file.interfaces.FileWriteCommand;

/**
 * An implementation of the abstract file write command based on the copy of a resource
 * @author Nicolas Thibault
 *
 */
public class ResourceFileWriteCommand implements FileWriteCommand {

	private String resourceRootPath;
	private String targetRootPath;
	
	
	
	@Override
	public void execute() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getRowCount() {
		return 0;
	}

}
