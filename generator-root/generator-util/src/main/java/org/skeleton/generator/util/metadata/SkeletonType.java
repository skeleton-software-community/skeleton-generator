package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

public enum SkeletonType {
	SPRING_HIBERNATE_CXF_RICHFACES("Spring Hibernate Cxf Richfaces"),
    SPRING_HIBERNATE_RICHFACES("Spring Hibernate Richfaces"),
    BASIC_SPRING_HIBERNATE_RICHFACES("Basic Spring Hibernate Richfaces");
	
	private static final Map<String, SkeletonType> reverseMap = new HashMap<String, SkeletonType>();
	static{
		for(SkeletonType skeletonType : values()){
			reverseMap.put(skeletonType.getValue(), skeletonType);
		}
	}
	
	private String value;
	
	private SkeletonType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static SkeletonType byValue(String value){
		SkeletonType skeletonType = reverseMap.get(value);
		if(skeletonType==null) {
			throw new IllegalArgumentException("No SkeletonType corresponding to value " + value);
		}
		return skeletonType;
	}
}
