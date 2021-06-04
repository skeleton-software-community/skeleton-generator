package org.sklsft.generator.skeletons.commands.impl.typed;

import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;




/**
 * Extension of a generic file write command to handle specificities of java files
 * <br/>Handles conservation of imports
 * @author Nicolas Thibault
 *
 */
public abstract class ScssFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * constructor
	 */
	public ScssFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.SCSS);
	}
}
