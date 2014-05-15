package org.skeleton.generator.bc.command.file.impl.conf;

import org.skeleton.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;

public abstract class PropertiesFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public PropertiesFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.PROPERTIES);
	}
}
