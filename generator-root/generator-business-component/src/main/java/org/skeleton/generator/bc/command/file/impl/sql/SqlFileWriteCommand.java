package org.skeleton.generator.bc.command.file.impl.sql;

import org.skeleton.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.skeleton.generator.model.metadata.FileType;

public abstract class SqlFileWriteCommand extends SingleFileWriteCommand{

	/*
	 * constructor
	 */
	public SqlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.SQL);
	}
}
