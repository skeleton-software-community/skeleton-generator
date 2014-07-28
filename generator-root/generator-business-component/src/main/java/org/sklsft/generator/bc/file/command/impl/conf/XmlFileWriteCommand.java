package org.sklsft.generator.bc.file.command.impl.conf;

import org.sklsft.generator.bc.file.command.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class XmlFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public XmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XML);
	}
}
