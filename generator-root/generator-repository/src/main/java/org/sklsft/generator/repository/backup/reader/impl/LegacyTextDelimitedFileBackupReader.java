package org.sklsft.generator.repository.backup.reader.impl;

import java.nio.charset.StandardCharsets;

import org.sklsft.generator.repository.backup.file.impl.LegacyTextDelimitedFileParserImpl;


public class LegacyTextDelimitedFileBackupReader extends TextDelimitedFileBackupReader {
	
	/*
	 * constructor
	 */
	public LegacyTextDelimitedFileBackupReader() {
		super(new LegacyTextDelimitedFileParserImpl(StandardCharsets.UTF_8));
	}
}
