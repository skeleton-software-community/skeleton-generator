package org.sklsft.generator.persistence.backup.reader.impl;

import java.nio.charset.StandardCharsets;

import org.sklsft.generator.persistence.backup.file.impl.LegacyTextDelimitedFileParserImpl;


public class LegacyTextDelimitedFileBackupReader extends TextDelimitedFileBackupReader {
	
	/*
	 * constructor
	 */
	public LegacyTextDelimitedFileBackupReader() {
		super(new LegacyTextDelimitedFileParserImpl(StandardCharsets.UTF_8));
	}
}
