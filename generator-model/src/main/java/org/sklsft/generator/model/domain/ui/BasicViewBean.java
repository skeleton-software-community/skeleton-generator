package org.sklsft.generator.model.domain.ui;

import java.util.List;



public class BasicViewBean {
	
	public String className;
	public String objectName;
	
	
	public FilterBean filter = new FilterBean();
	public String sortingClassName;
	
	public String baseMapperClassName;
	public String mapperClassName;
	public String mapperObjectName;
	
	public List<ViewProperty> properties;

}
