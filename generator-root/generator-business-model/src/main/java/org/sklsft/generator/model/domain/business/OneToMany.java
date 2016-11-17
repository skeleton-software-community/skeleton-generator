package org.sklsft.generator.model.domain.business;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.domain.ui.BasicViewBean;
import org.sklsft.generator.model.domain.ui.FormBean;

public class OneToMany {

	public Bean referenceBean;
    public Property referenceProperty;
    public Bean parentBean;
    public String collectionName;
    public String collectionGetterName;
    public String collectionSetterName;
    
    public BasicViewBean basicViewBean;
    public FormBean formBean;
    
    
	/**
	 * get the list of properties that will be available in a basic view bean
	 * 
	 * @return
	 */
	private List<Property> getVisibleProperties() {
		
		return referenceBean.getVisiblePropertiesExcludingField(referenceProperty.name);
	}
	
	public List<Property> getBasicViewProperties() {
		List<Property> result = new ArrayList<>();
		
		for (Property property:getVisibleProperties()) {
			if (property.visibility.isListVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
	
	public List<Property> getFormProperties() {
		List<Property> result = new ArrayList<>();
		
		for (Property property:getVisibleProperties()) {
			if (property.visibility.isDetailVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
}
