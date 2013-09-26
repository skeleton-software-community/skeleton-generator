package com.skeleton.generator.repository.file.interfaces;

import java.io.IOException;
import java.util.List;

import com.skeleton.generator.exception.InvalidFileException;

public interface FileManager {

	List<String[]> readData(String filePath) throws IOException, InvalidFileException;
	
	void writeData (String folderPath, String fileName, List<String[]> content) throws IOException;
}
