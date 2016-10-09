package org.sklsft.generator.bash.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Launches help command<br>
 * reads a file where the text will be displayed
 * @author Nicolas Thibault
 *
 */
public class HelpLauncher {
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(HelpLauncher.class);
	
	public static void main(String[] args) {
		System.out.println(getHelpContent());
	}

	private static String getHelpContent() {
		try (InputStream stream = HelpLauncher.class.getResourceAsStream("HelpContent.txt");){
			return IOUtils.toString(stream, StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("failed to get help content : " + e.getMessage(), e);
			return "";
		}
	}
}
