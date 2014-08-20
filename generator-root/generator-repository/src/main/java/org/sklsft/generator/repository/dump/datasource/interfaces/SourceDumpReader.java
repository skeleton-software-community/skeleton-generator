package org.sklsft.generator.repository.dump.datasource.interfaces;

import java.util.List;

import org.sklsft.generator.exception.ReadDumpFailureException;

/**
 * 
 * @author Michael Fernandez
 *
 */
public interface SourceDumpReader {

	List<String[]> readDumpData(String script) throws ReadDumpFailureException;	

}
