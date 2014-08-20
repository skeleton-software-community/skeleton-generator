package org.sklsft.generator.bc.dump.writer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.file.impl.CsvFileParserImpl;
import org.springframework.stereotype.Component;

/**
 * Write the business content into a file in the DUMP folder
 * 
 * @author Michael Fernandez
 */
@Component
public class DumpFileCommand {

	private static final String BACKUP_FOLDER = "DUMP";
	private static final String FILE_TYPE = ".txt";
	private static final String SEPARATOR = "$";
	
	public void writeDataFile(Table table, List<String[]> content) throws IOException {
		if (!content.isEmpty()) {
			int nbColumns = content.get(0).length;
			
			CsvFileParserImpl csvWriter = new CsvFileParserImpl(nbColumns,StandardCharsets.UTF_8,SEPARATOR);
			csvWriter.writeData(getDumpFilePath(table), getDumpFileName(table),  content);
			
		}
	}
	
	private String getDumpFilePath(Table table) {
		return table.myPackage.model.project.sourceFolder + File.separator + BACKUP_FOLDER + File.separator + table.myPackage.name;		
	}
	private String getDumpFileName(Table table) {
		return table.originalName + FILE_TYPE; 
	}
}
