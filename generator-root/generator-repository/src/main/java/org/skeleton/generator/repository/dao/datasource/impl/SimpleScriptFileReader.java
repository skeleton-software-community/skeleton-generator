package org.skeleton.generator.repository.dao.datasource.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * simply converts a sript file to its content as a String
 * @author Nicolas Thibault
 *
 */
public class SimpleScriptFileReader {

	private String scriptFilePath;
	private Charset charset;
	
	public SimpleScriptFileReader(String scriptFilePath) {
		this.charset = StandardCharsets.ISO_8859_1;
		this.scriptFilePath = scriptFilePath;
	}
	
	public SimpleScriptFileReader(String scriptFilePath, Charset charset) {
		this.charset = charset;
		this.scriptFilePath = scriptFilePath;
	}

	public String readScript() throws IOException {
		Path path  = Paths.get(scriptFilePath);
		byte[] bytes = Files.readAllBytes(path);
        return new String(bytes,charset);
	}
}
