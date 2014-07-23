package org.sklsft.generator.bc.command.file.impl.sql;

import org.sklsft.generator.bc.command.file.impl.SingleFileWriteCommand;
import org.sklsft.generator.model.metadata.FileType;

public abstract class SqlFileWriteCommand extends SingleFileWriteCommand{

	/*
	 * constructor
	 */
	public SqlFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.SQL);
	}
}
