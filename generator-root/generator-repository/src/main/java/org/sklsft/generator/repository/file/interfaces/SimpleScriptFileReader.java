package org.sklsft.generator.repository.file.interfaces;

import java.io.IOException;
import java.nio.charset.Charset;

public interface SimpleScriptFileReader {

	public String readScript(String scriptFilePath, Charset charset) throws IOException;
	
	public String readScript(String scriptFilePath) throws IOException;
}
	