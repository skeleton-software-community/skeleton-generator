package org.sklsft.generator.persistence.backup.reader.impl;

import org.sklsft.generator.persistence.backup.file.impl.SemiColonExcelCsvFileParserImpl;


public class StandardCsvFileBackupReader extends TextDelimitedFileBackupReader {
	
	/*
	 * constructor
	 */
	public StandardCsvFileBackupReader() {
		super(new SemiColonExcelCsvFileParserImpl());
	}
}
