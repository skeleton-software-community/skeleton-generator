package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

public enum Visibility {
	VISIBLE ("", true, true),
    NOT_LIST ("Not List", false, true),
    NOT_VISIBLE ("Not Visible", false, false);
	
	private static final Map<String, Visibility> reverseMap = new HashMap<String, Visibility>();
	static{
		for(Visibility visibility : values()){
			reverseMap.put(visibility.getValue(), visibility);
		}
	}
	
	private String value;
	private boolean listVisible;
	private boolean detailVisible;
	
	private Visibility(String value, boolean listVisible, boolean detailVisible){
		this.value = value;
		this.listVisible = listVisible;
		this.detailVisible = detailVisible;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isListVisible() {
		return listVisible;
	}

	public boolean isDetailVisible() {
		return detailVisible;
	}

	public static Visibility byValue(String value){
		Visibility visibility = reverseMap.get(value);
		if(visibility==null) {
			throw new IllegalArgumentException("No Visibility corresponding to value " + value);
		}
		return visibility;
	}
}
