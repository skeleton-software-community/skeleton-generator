package org.sklsft.generator.bc.command.file.impl.presentation;

import org.sklsft.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class XhtmlFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public XhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XHTML);
	}
}
