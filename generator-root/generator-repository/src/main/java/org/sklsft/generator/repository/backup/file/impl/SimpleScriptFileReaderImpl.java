package org.sklsft.generator.repository.backup.file.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sklsft.generator.repository.backup.file.interfaces.SimpleScriptFileReader;
import org.springframework.stereotype.Component;

/**
 * simply converts a sript file to its content as a String
 * @author Nicolas Thibault
 *
 */
@Component
public class SimpleScriptFileReaderImpl implements SimpleScriptFileReader {

	public String readScript(String scriptFilePath, Charset charset) throws IOException {
		Path path  = Paths.get(scriptFilePath);
		byte[] bytes = Files.readAllBytes(path);
        return new String(bytes,charset);
	}
	
	public String readScript(String scriptFilePath) throws IOException {
		return readScript(scriptFilePath, StandardCharsets.UTF_8);
	}
}
