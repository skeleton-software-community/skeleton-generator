package org.sklsft.generator.skeletons.commands.impl.typed;

import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;

public abstract class XhtmlFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public XhtmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XHTML);
	}
}
