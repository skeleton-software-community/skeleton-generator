package org.sklsft.generator.bc.command.file.impl.batch;

import org.sklsft.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class BatchFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public BatchFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.BAT);
	}
}
