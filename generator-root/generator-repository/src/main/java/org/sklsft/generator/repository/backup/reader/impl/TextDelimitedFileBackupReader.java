package org.sklsft.generator.repository.backup.reader.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.repository.backup.file.impl.CsvFileParserImpl;
import org.sklsft.generator.repository.backup.file.interfaces.CsvFileParser;
import org.sklsft.generator.repository.backup.file.model.CsvFile;
import org.sklsft.generator.repository.backup.reader.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.reader.model.BackupArguments;


public class TextDelimitedFileBackupReader implements BackupArgumentReader {

	/*
	 * properties
	 */
	private CsvFileParser csvFileParser;
	
	/*
	 * constructor
	 */
	public TextDelimitedFileBackupReader() {
		this.csvFileParser = new CsvFileParserImpl(StandardCharsets.UTF_8);
	}
	
	public BackupArguments readBackupArgs(String backupFilePath)  {
		BackupArguments result = new BackupArguments();
		result.setArguments(readArgs(backupFilePath));
		result.setArgumentsTyped(false);
		return result;
	}
	

	private List<Object[]> readArgs(String backupFilePath){
		try {
			CsvFile csvFile = csvFileParser.readData(backupFilePath);
			
			return csvFile.getData();
			
		} catch (IOException | InvalidFileException e) {
			throw new ReadBackupFailureException("failed to read backup", e);
		}
	}
}
