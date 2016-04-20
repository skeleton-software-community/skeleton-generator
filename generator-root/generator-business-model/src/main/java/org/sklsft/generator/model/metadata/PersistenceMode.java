package org.sklsft.generator.model.metadata;

import java.util.HashMap;
import java.util.Map;

public enum PersistenceMode {
	CSV(".txt"),
    XML(".xml"),
    CMD(".comd");
	
	private static final Map<String, PersistenceMode> reverseMap = new HashMap<String, PersistenceMode>();
	static{
		for(PersistenceMode skeletonType : values()){
			reverseMap.put(skeletonType.getExtension(), skeletonType);
		}
	}
	
	private String extension;
	
	private PersistenceMode(String value){
		this.extension = value;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public static PersistenceMode byExtension(String value){
		PersistenceMode persistenceMode = reverseMap.get(value);
		if(persistenceMode==null) {
			throw new IllegalArgumentException("No PersistenceMode corresponding to value " + value);
		}
		return persistenceMode;
	}
}
