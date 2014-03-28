package org.skeleton.generator.util.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * For the current release, the following skeletons are supported :
 * <li>RichFaces web application with spring and hibernate
 * <li>A simple version of a RichFaces web application with spring and hibernate
 * @author Nicolas Thibault
 *
 */
public enum SkeletonType {
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
