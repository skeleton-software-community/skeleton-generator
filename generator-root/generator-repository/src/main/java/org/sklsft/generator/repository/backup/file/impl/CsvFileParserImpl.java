package org.sklsft.generator.repository.backup.file.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.repository.backup.file.interfaces.CsvFileParser;
import org.sklsft.generator.repository.backup.file.model.CsvFile;


public class CsvFileParserImpl implements CsvFileParser {
	
	/*
	 * properties
	 */
	private Charset charset;
	private String separator;
	
	
	/*
	 * constructor
	 */
	public CsvFileParserImpl() {
		this.charset = StandardCharsets.ISO_8859_1;
		this.separator = "\\$";
	}
	
	public CsvFileParserImpl(Charset charset) {
		this.charset = charset;
		this.separator = "\\$";
	}
	
	public CsvFileParserImpl(Charset charset, String separator) {
		this.charset = charset;
		this.separator = separator;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.skeleton.generator.repository.dao.interfaces.MetaDataFileManager#readMataDataFile(java.lang.String)
	 */
	@Override
	public CsvFile readData(String filePath) throws IOException, InvalidFileException {

		Path path = Paths.get(filePath);
		List<Object[]> tokensList = new ArrayList<Object[]>();
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset);) {
			
			String line = null;
			while (!StringUtils.isEmpty(line = reader.readLine())) {
				String[] tokens = line.split(separator, -1);
				
				tokensList.add(tokens);
			}
		}
		
		CsvFile result = new CsvFile();
		result.setPopulateCommandType(PopulateCommandType.INSERT); //TODO handle other types
		result.setData(tokensList);			
		
		return result;
	}
}
