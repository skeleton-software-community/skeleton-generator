package org.sklsft.generator.skeletons.commands.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.sklsft.generator.model.metadata.FileType;
import org.sklsft.generator.skeletons.commands.interfaces.FileWriteCommand;



/**
 * generic abstract implementation of a {@link FileWriteCommand}
 * @author Nicolas Thibault
 *
 */
public abstract class SingleFileWriteCommand implements FileWriteCommand {

	/*
	 * properties
	 */
	protected static final char CHAR_34 = (char)34;
	private static final String SKIP_LINE = "\n";
	protected FileType fileType;
	private String qualifiedFileName;
	private String folderName;
	
	protected StringWriter writer;
	private int rowCount = 0;
	protected Path filePath;
	
	
	/*
	 * getters and setters
	 */
	public int getRowCount() {
		return rowCount;
	}
	
	
	/**
	 * constructor
	 */
	public SingleFileWriteCommand(String folderName, String fileName, FileType fileType) {
		writer = new StringWriter();
		this.fileType = fileType;
		this.folderName = folderName;
		this.qualifiedFileName = fileName + fileType.getExtension();
		this.filePath = Paths.get(folderName + File.separator + qualifiedFileName);
	}
	
	public String getLabel() {
		return filePath.toString();
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
	
	/**
	 * generic method to override to specialize the behavior of the command
	 * @throws IOException
	 */
	protected abstract void writeContent() throws IOException;
	
	/**
	 * appends content to the embedded {@link StringWriter}
	 * @param content
	 */
	protected final void write(String content) {
		writer.append(content);
	}
	
	/**
	 * appends line then skips line
	 * @param line
	 */
	protected final void writeLine(String line) {
		write(line);
		skipLine();
	}
	
	/**
	 * skips line
	 */
	protected final void skipLine() {
        writer.append(SKIP_LINE);
        rowCount++;
    }
	
	/**
	 * extract non generated content from a file to keep it after next generation
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * appends non generated content to be kept at next generation
	 * @throws IOException
	 */
	protected final void writeNotOverridableContent() throws IOException {

		String content = getNotOverridableContent();

		writeLine(fileType.getSpecificCodeStartMark());
		write(content);
		writeLine(fileType.getSpecificCodeEndMark());
	}
        

}
