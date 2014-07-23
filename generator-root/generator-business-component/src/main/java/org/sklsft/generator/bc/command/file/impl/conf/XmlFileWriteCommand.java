package org.sklsft.generator.bc.command.file.impl.conf;

import org.sklsft.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class XmlFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public XmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XML);
	}
}
