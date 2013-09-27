package com.skeleton.generator.bl.command.file.interfaces;

import java.io.IOException;

public interface FileWriteCommand {

	void execute() throws IOException;
	
	int getRowCount();
}
