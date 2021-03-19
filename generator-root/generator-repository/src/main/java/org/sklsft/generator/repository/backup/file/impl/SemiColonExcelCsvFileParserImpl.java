package org.sklsft.generator.repository.backup.file.impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.repository.backup.file.interfaces.TextDelimitedFileParser;
import org.sklsft.generator.repository.backup.file.model.TextDelimitedFile;


public class SemiColonExcelCsvFileParserImpl implements TextDelimitedFileParser {
	
	
	

	@Override
	public TextDelimitedFile readData(String filePath) throws IOException, InvalidFileException {

		//Path path = Paths.get(filePath);
		List<Object[]> tokensList = new ArrayList<>();
				
		try (Reader reader = new FileReader(filePath);) {
			
			Iterable<CSVRecord> records = CSVFormat.newFormat(';').withQuote('"').withFirstRecordAsHeader().withAllowMissingColumnNames().parse(reader);
			for (CSVRecord record : records) {
				Object[] tokens = new Object[record.size()];
				for (int i = 0;i<record.size();i++) {
					tokens[i] = record.get(i);
				}
				tokensList.add(tokens);
			}
		}
		
		TextDelimitedFile result = new TextDelimitedFile();
		result.setData(tokensList);			
		
		return result;
	}
}
