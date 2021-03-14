package org.sklsft.generator.repository.backup.reader.impl;

import org.sklsft.generator.repository.backup.file.impl.StandardCsvFileParserImpl;


public class StandardCsvFileBackupReader extends TextDelimitedFileBackupReader {
	
	/*
	 * constructor
	 */
	public StandardCsvFileBackupReader() {
		super(new StandardCsvFileParserImpl());
	}
}
