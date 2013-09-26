package com.skeleton.generator.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum RelationType {
	MANY_TO_ONE("many to one"),
    MANY_TO_ONE_COMPONENT("many to one component"),
    UNIQUE("unique"),
    UNIQUE_COMPONENT("unique component"),
    ONE_TO_ONE("one to one"),
    ONE_TO_ONE_COMPONENT("one to one component"),
    PROPERTY("");
    
    private static final Map<String, RelationType> reverseMap = new HashMap<String, RelationType>();
	static{
		for(RelationType relationType : values()){
			reverseMap.put(relationType.getValue(), relationType);
		}
	}
	
	private String value;
	
	private RelationType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static RelationType byValue(String value){
		RelationType relationType = reverseMap.get(value);
		if(relationType==null) {
			throw new IllegalArgumentException("No RelationType corresponding to value " + value);
		}
		return relationType;
	}
	
	public static Boolean isUnique(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE:
                return true;

            case ONE_TO_ONE:
                return true;

            case ONE_TO_ONE_COMPONENT:
                return true;

            default:
                return false;

        }
    }

    public static Boolean isComponentLink(RelationType relationType)
    {
        switch (relationType)
        {
            case UNIQUE_COMPONENT:
                return true;

            case ONE_TO_ONE_COMPONENT:
                return true;

            case MANY_TO_ONE_COMPONENT:
                return true;

            default:
                return false;

        }
    }
}
