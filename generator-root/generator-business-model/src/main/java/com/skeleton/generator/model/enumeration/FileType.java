package com.skeleton.generator.model.enumeration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public enum FileType {
	JAVA ("JAVA", ".java", StandardCharsets.UTF_8, "/* Specific Code Start */", "/* Specific Code End */"),
    SQL ("SQL", ".sql", StandardCharsets.UTF_8, "-- Specific Code Start --", "-- Specific Code End --"),
    XML ("XML", ".xml", StandardCharsets.UTF_8, "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    XHTML ("XHTML", ".xhtml", StandardCharsets.UTF_8, "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    BAT ("BAT", ".bat", StandardCharsets.UTF_8, "# Specific Code Start", "# Specific Code End"),
    PROPERTIES ("PROPERTIES", ".properties", StandardCharsets.UTF_8, "-- Specific Code Start --", "-- Specific Code End --");
	
	private static final Map<String, FileType> reverseMap = new HashMap<String, FileType>();
	static{
		for(FileType fileType : FileType.values()){
			reverseMap.put(fileType.getValue(), fileType);
		}
	}
	
	private String value;
	private String extension;
	private Charset encoding;
	private String specificCodeStartMark;
	private String specificCodeEndMark;
	
	private FileType(String value, String extension, Charset encoding, String specificCodeStartMark, String specificCodeEndMark){
		this.value = value;
		this.extension = extension;
		this.encoding = encoding;
		this.specificCodeStartMark = specificCodeStartMark;
		this.specificCodeEndMark = specificCodeEndMark;
	}
	
	public String getValue() {
		return value;
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
	
	public static FileType byValue(String value){
		FileType fileType = reverseMap.get(value);
		if(fileType==null) {
			throw new IllegalArgumentException("No FileType corresponding to value " + value);
		}
		return fileType;
	}
}
