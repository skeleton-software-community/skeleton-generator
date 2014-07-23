package org.sklsft.generator.bc.command.file.impl.conf;

import org.sklsft.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class PropertiesFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public PropertiesFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.PROPERTIES);
	}
}
