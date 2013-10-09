package org.skeleton.generator.bc.command.file.impl.conf;

import org.skeleton.generator.bc.command.file.impl.AbstractFileWriteCommand;
import org.skeleton.generator.util.metadata.FileType;

public abstract class PropertiesFileWriteCommand extends AbstractFileWriteCommand {

	/*
	 * constructor
	 */
	public PropertiesFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.PROPERTIES);
	}
}
