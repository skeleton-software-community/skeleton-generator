package org.sklsft.generator.skeletons.commands.impl.typed;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.sklsft.generator.model.metadata.files.FileType;
import org.sklsft.generator.skeletons.commands.impl.SingleFileWriteCommand;




/**
 * Extension of a generic file write command to handle specificities of java files
 * <br/>Handles conservation of imports
 * @author Nicolas Thibault
 *
 */
public abstract class TsFileWriteCommand extends SingleFileWriteCommand {

	/*
	 * properties
	 */
	protected Set<String> imports;
	
	
	/*
	 * constructor
	 */
	public TsFileWriteCommand(String folderName, String fileName) {
		super(folderName, fileName, FileType.TS);
		this.imports = new TreeSet<String>();
	}

	private final void fetchImports() throws IOException {
		if (Files.exists(filePath)) {
			List<String> lines = Files.readAllLines(filePath,
					fileType.getEncoding());
			
			for (String line : lines) {
				if (line.startsWith("import ")){
                    imports.add(line);
                }
			}
		}
	}
	
	protected abstract void fetchSpecificImports();
	
	protected final void writeImports() throws IOException{
		fetchImports();
		fetchSpecificImports();
		
		for (String imported:imports) {
			writeLine(imported);
		}
	}

	
	

}
