package org.sklsft.generator.persistence.backup.file.impl;

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
import org.sklsft.generator.model.exception.InvalidFileException;
import org.sklsft.generator.persistence.backup.file.interfaces.TextDelimitedFileParser;
import org.sklsft.generator.persistence.backup.file.model.TextDelimitedFile;


public class LegacyTextDelimitedFileParserImpl implements TextDelimitedFileParser {
	
	/*
	 * properties
	 */
	private Charset charset;
	private String separator;
	
	
	/*
	 * constructor
	 */
	public LegacyTextDelimitedFileParserImpl() {
		this.charset = StandardCharsets.ISO_8859_1;
		this.separator = "\\$";
	}
	
	public LegacyTextDelimitedFileParserImpl(Charset charset) {
		this.charset = charset;
		this.separator = "\\$";
	}
	
	public LegacyTextDelimitedFileParserImpl(Charset charset, String separator) {
		this.charset = charset;
		this.separator = separator;
	}
	

	@Override
	public TextDelimitedFile readData(String filePath) throws IOException, InvalidFileException {

		Path path = Paths.get(filePath);
		List<Object[]> tokensList = new ArrayList<>();
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset);) {
			
			String line = null;
			while (!StringUtils.isEmpty(line = reader.readLine())) {
				String[] tokens = line.split(separator, -1);
				
				tokensList.add(tokens);
			}
		}
		
		TextDelimitedFile result = new TextDelimitedFile();
		result.setData(tokensList);			
		
		return result;
	}
}
