package org.sklsft.generator.skeletons.commands.impl.typed;

import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;

public abstract class PropertiesFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public PropertiesFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.PROPERTIES);
	}
}
