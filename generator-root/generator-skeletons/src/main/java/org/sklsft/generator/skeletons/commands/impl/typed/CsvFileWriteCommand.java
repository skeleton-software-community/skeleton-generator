package org.sklsft.generator.skeletons.commands.impl.typed;

import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;

public abstract class CsvFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public CsvFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.CSV);
	}
}
