package org.sklsft.generator.repository.backup.file.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.repository.backup.file.interfaces.TextDelimitedFileParser;
import org.sklsft.generator.repository.backup.file.model.TextDelimitedFile;


public class StandardCsvFileParserImpl implements TextDelimitedFileParser {
	
	
	

	@Override
	public TextDelimitedFile readData(String filePath) throws IOException, InvalidFileException {

		//Path path = Paths.get(filePath);
		List<Object[]> tokensList = new ArrayList<>();
				
		try (Reader reader = new FileReader(filePath);) {
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(reader);
			for (CSVRecord record : records) {
				Object[] tokens = new Object[record.size()];
				for (int i = 0;i<record.size();i++) {
					tokens[i] = record.get(i);
				}
			}
		}
		
		TextDelimitedFile result = new TextDelimitedFile();
		result.setData(tokensList);			
		
		return result;
	}
}
