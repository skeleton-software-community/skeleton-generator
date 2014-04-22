package org.skeleton.generator.bc.command.file.impl.conf;

import org.skeleton.generator.bc.command.file.impl.AbstractFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;

public abstract class XmlFileWriteCommand extends AbstractFileWriteCommand {

	/*
	 * constructor
	 */
	public XmlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.XML);
	}
}
