package com.skeleton.generator.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum FileType {
	JAVA ("JAVA", ".java", "UTF8", "/* Specific Code Start */", "/* Specific Code End */"),
    SQL ("SQL", ".sql", "UTF8", "/* Specific Code Start */", "/* Specific Code End */"),
    XML ("XML", ".xml", "UTF8", "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    XHTML ("XHTML", ".xhtml", "UTF8", "<!-- Specific Code Start -->", "<!-- Specific Code End -->"),
    BAT ("BAT", ".bat", "UTF8", "# Specific Code Start", "# Specific Code End"),
    PROPERTIES ("PROPERTIES", ".properties", "UTF8", "-- Specific Code Start --", "-- Specific Code End --");
	
	private static final Map<String, FileType> reverseMap = new HashMap<String, FileType>();
	static{
		for(FileType fileType : FileType.values()){
			reverseMap.put(fileType.getValue(), fileType);
		}
	}
	
	private String value;
	private String extension;
	private String encoding;
	private String specificCodeStartMark;
	private String specificCodeEndMark;
	
	private FileType(String value, String extension, String encoding, String specificCodeStartMark, String specificCodeEndMark){
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
	
	public String getEncoding() {
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
