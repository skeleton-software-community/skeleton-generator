package com.skeleton.generator.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Format {
	DEFAULT (""),
    TWO_DECIMALS ("two decimals"),
    FOUR_DECIMALS ("four decimals"),
    DATE ("date"),
    DATE_AND_TIME ("date and time");
	
	private static final Map<String, Format> reverseMap = new HashMap<String, Format>();
	static{
		for(Format format : values()){
			reverseMap.put(format.getValue(), format);
		}
	}
	
	private String value;
	
	private Format(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Format byValue(String value){
		Format format = reverseMap.get(value);
		if(format==null) {
			throw new IllegalArgumentException("No Format corresponding to value " + value);
		}
		return format;
	}
}
