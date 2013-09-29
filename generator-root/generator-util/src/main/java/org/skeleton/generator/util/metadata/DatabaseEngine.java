package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

public enum DatabaseEngine {
	MYSQL ("MySQL"),
    MSSQL ("SQL Server"),
    POSTGRESQL ("Postgresql"),
    ORACLE ("Oracle");
    
    private static final Map<String, DatabaseEngine> reverseMap = new HashMap<String, DatabaseEngine>();
	static{
		for(DatabaseEngine databaseEngine : DatabaseEngine.values()){
			reverseMap.put(databaseEngine.getValue(), databaseEngine);
		}
	}
	
	private String value;
	
	private DatabaseEngine(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static DatabaseEngine byValue(String value){
		DatabaseEngine databaseEngine = reverseMap.get(value);
		if(databaseEngine==null) {
			throw new IllegalArgumentException("No Database corresponding to value " + value);
		}
		return databaseEngine;
	}
}
