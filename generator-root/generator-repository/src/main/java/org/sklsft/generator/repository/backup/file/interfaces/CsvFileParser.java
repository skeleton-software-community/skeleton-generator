package org.sklsft.generator.repository.backup.file.interfaces;

import java.io.IOException;
import java.util.List;

import org.sklsft.generator.exception.InvalidFileException;

/**
 * provides two methodes to :
 * <li>read a csv file as a list of string arrays
 * <li>write a csv file from a list of string arrays
 * @author Nicolas Thibault
 *
 */
public interface CsvFileParser {

	List<String[]> readData(String filePath) throws IOException, InvalidFileException;
	
	void writeData (String folderPath, String fileName, List<String[]> content) throws IOException;
}
