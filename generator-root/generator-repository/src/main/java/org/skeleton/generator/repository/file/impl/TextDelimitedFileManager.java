package org.skeleton.generator.repository.file.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.repository.file.interfaces.FileManager;


public class TextDelimitedFileManager implements FileManager {
	
	/*
	 * properties
	 */
	private int columnNumber;
	private Charset charset = Charset.forName("ISO-8859-1");
	private String separator = "\\$";
	
	
	/*
	 * constructor
	 */
	public TextDelimitedFileManager(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.MetaDataFileManager#readMataDataFile(java.lang.String)
	 */
	@Override
	public List<String[]> readData(String filePath) throws IOException, InvalidFileException {

		Path path = Paths.get(filePath);
		List<String[]> tokensList = new ArrayList<String[]>();
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset);) {
			
			String line = null;
			while (!StringUtils.isEmpty(line = reader.readLine())) {
				String[] tokens = line.split(separator, columnNumber);
				
				if (tokens.length != columnNumber) {
					throw new InvalidFileException("Invalid number of tokens");
				}
				
				tokensList.add(tokens);
			}
			
			return tokensList;
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.MetaDataFileManager#writeMetaDataFile(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void writeData(String folderPath, String fileName, List<String[]> content) throws IOException {
		
		Path path  = Paths.get(folderPath);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		path = Paths.get(folderPath + File.separator + fileName);
		
		try (BufferedWriter writer = Files.newBufferedWriter(path, charset);) {
			
			int i = 0;
			for (String[] tokens:content) {
				
				String line = "";
				int j = 0;
				while (j < tokens.length - 1) {
					line += tokens[j];
					line += separator;
					j++;
				}
				line += tokens[j];
				
				writer.append(line);
				
				if (i < content.size() -1) {
					writer.newLine();
				}
				i++;
			}
			writer.flush();
		}
	}

}
