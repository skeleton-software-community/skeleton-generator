package org.sklsft.generator.model.metadata.files;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Different kind of files can be generated :
 * <li>.java
 * <li>.sql
 * <li>.xml
 * <li>.xhtml
 * <li>.bat
 * <li>.properties
 * @author Nicolas Thibault
 *
 */
@XmlEnum(String.class)
public enum FileType {
	JAVA (".java", StandardCharsets.UTF_8, "/* Specific Code Start */", "/* Specific Code End */"),
    SQL (".sql", StandardCharsets.UTF_8, "-- Specific Code Start --", "-- Specific Code End --"),
    XML (".xml", StandardCharsets.UTF_8, "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    XHTML (".xhtml", StandardCharsets.UTF_8, "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    JS (".js", StandardCharsets.UTF_8, "/* Specific Code Start */", "/* Specific Code End */"),
    CSS (".css", StandardCharsets.UTF_8, "/* Specific Code Start */", "/* Specific Code End */"),
    BAT (".bat", StandardCharsets.UTF_8, "# Specific Code Start", "# Specific Code End"),
    CSV (".csv", StandardCharsets.UTF_8, "# Specific Code Start", "# Specific Code End"),
    PROPERTIES (".properties", StandardCharsets.ISO_8859_1, "# Specific Code Start", "# Specific Code End");
	
	private String extension;
	private Charset encoding;
	private String specificCodeStartMark;
	private String specificCodeEndMark;
	
	private FileType(String extension, Charset encoding, String specificCodeStartMark, String specificCodeEndMark){
		this.extension = extension;
		this.encoding = encoding;
		this.specificCodeStartMark = specificCodeStartMark;
		this.specificCodeEndMark = specificCodeEndMark;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public Charset getEncoding() {
		return encoding;
	}
	
	public String getSpecificCodeStartMark() {
		return specificCodeStartMark;
	}
	
	public String getSpecificCodeEndMark() {
		return specificCodeEndMark;
	}
}
