package org.skeleton.generator.bc.command.file.impl.batch;

import org.skeleton.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;

public abstract class BatchFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public BatchFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.BAT);
	}
}
