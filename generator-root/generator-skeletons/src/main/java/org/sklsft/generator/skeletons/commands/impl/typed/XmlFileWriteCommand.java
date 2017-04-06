package org.sklsft.generator.skeletons.commands.impl.typed;

import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;

public abstract class XmlFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public XmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XML);
	}
}
