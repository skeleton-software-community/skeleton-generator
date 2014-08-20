package org.sklsft.generator.bc.dump.reader;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.dump.datasource.impl.SourceDumpReaderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Read the business content of a table
 * 
 * @author Michael Fernandez
 */
@Component
public class DataTableReader {

	@Autowired
	private ReadScriptGenerator scriptGenerator;
	
	public List<String[]> readData(DataSource dataSource, Table table) {
		String script = scriptGenerator.generateReadScript(table);
		SourceDumpReaderImpl reader = new SourceDumpReaderImpl(dataSource, table);
		return reader.readDumpData(script);
	}

}
