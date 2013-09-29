package org.skeleton.generator.repository.file.interfaces;

import java.io.IOException;
import java.util.List;

import org.skeleton.generator.exception.InvalidFileException;


public interface FileManager {

	List<String[]> readData(String filePath) throws IOException, InvalidFileException;
	
	void writeData (String folderPath, String fileName, List<String[]> content) throws IOException;
}
