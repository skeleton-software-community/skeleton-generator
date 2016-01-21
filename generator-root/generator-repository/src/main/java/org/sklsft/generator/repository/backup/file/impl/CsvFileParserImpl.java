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
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.model.metadata.DataType;
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
		List<String[]> tokensList = new ArrayList<String[]>();
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset);) {
			
			String line = null;
			while (!StringUtils.isEmpty(line = reader.readLine())) {
				String[] tokens = line.split(separator, -1);
				
				tokensList.add(tokens);
			}
		}
		
		CsvFile result = new CsvFile();
		result.setPopulateCommandType(PopulateCommandType.INSERT); //TODO handle other types
		
		try {
			String[] firstTokens = tokensList.get(0);
			
			result.setTypes(new DataType[firstTokens.length]);
			
			for (int i=0;i<firstTokens.length;i++) {
				result.getTypes()[i] = DataType.valueOf(firstTokens[i]);
			}
			
			result.setData(new ArrayList<String[]>());
			
			for (int i=1;i<tokensList.size();i++) {
				result.getData().add(tokensList.get(i));
			}
			
		} catch (Exception e) {
			throw new ReadBackupFailureException("failed to parse backup csv file " + filePath, e);
		}
		
		return result;
	}
}
