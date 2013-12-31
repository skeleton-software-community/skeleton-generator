package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

public enum PersistenceMode {
	CSV("csv"),
    XML("xml");
	
	private static final Map<String, PersistenceMode> reverseMap = new HashMap<String, PersistenceMode>();
	static{
		for(PersistenceMode skeletonType : values()){
			reverseMap.put(skeletonType.getValue(), skeletonType);
		}
	}
	
	private String value;
	
	private PersistenceMode(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static PersistenceMode byValue(String value){
		PersistenceMode persistenceMode = reverseMap.get(value);
		if(persistenceMode==null) {
			throw new IllegalArgumentException("No PersistenceMode corresponding to value " + value);
		}
		return persistenceMode;
	}
}
