package org.skeleton.generator.bc.command.file.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.skeleton.generator.bc.command.file.interfaces.FileWriteCommand;
import org.skeleton.generator.util.metadata.FileType;



/**
 * generic abstract implementation of a {@link FileWriteCommand}
 * @author Nicolas Thibault
 *
 */
public abstract class AbstractFileWriteCommand implements FileWriteCommand {

	/*
	 * properties
	 */
	private static final String SKIP_LINE = "\n";
	protected FileType fileType;
	private String qualifiedFileName;
	private String folderName;
	
	private StringWriter writer;
	private int rowCount = 0;
	protected Path filePath;
	
	
	/*
	 * getters and setters
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	
	/*
	 * constructor
	 */
	public AbstractFileWriteCommand(String folderName, String fileName, FileType fileType) {
		writer = new StringWriter();
		this.fileType = fileType;
		this.folderName = folderName;
		this.qualifiedFileName = fileName + fileType.getExtension();
		this.filePath = Paths.get(folderName + File.separator + qualifiedFileName);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.bl.command.file.interfaces.FileWriteCommand#execute()
	 */
	@Override
	public void execute() throws IOException {
		Path folderPath = Paths.get(folderName);
		if (!Files.exists(folderPath)){
			Files.createDirectories(folderPath);
		}
		
		writeContent();
		
		try (BufferedWriter fileWriter = Files.newBufferedWriter(filePath, fileType.getEncoding());){
			fileWriter.write(writer.toString());
			fileWriter.flush();
		}
	}
	
	protected abstract void writeContent() throws IOException;
	
	
	protected final void write(String content) {
		writer.append(content);
	}
	
	protected final void writeLine(String line) {
		write(line);
		skipLine();
	}
	
	protected final void skipLine() {
        writer.append(SKIP_LINE);
        rowCount++;
    }
	
	private final String getNotOverridableContent() throws IOException {

		if (Files.exists(filePath)) {
			List<String> lines = Files.readAllLines(filePath,
					fileType.getEncoding());
			boolean isNotOverridable = false;
			StringWriter notOverridableContentWriter = new StringWriter();
			for (String line : lines) {
				if (line.contains(fileType.getSpecificCodeStartMark())) {
					isNotOverridable = true;
				} else if (line.contains(fileType.getSpecificCodeEndMark())) {
					isNotOverridable = false;
				} else {
					if (isNotOverridable) {
						notOverridableContentWriter.append(line + SKIP_LINE);
					}
				}
			}
			return notOverridableContentWriter.toString();
		}
		return "";
	}
	
	
	protected final void writeNotOverridableContent() throws IOException {

		String content = getNotOverridableContent();

		writeLine(fileType.getSpecificCodeStartMark());
		write(content);
		writeLine(fileType.getSpecificCodeEndMark());
	}
        

}
