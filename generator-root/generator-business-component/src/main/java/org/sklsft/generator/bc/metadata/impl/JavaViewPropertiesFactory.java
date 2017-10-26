package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.bc.metadata.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.util.naming.JavaClassNaming;
import org.springframework.stereotype.Component;

@Component("javaViewPropertiesFactory")
public class JavaViewPropertiesFactory implements ViewPropertiesFactory {

	public List<ViewProperty> getViewProperties(Property myProperty) {

		List<ViewProperty> result = new ArrayList<>();
		
		if (myProperty.referenceBean == null) {
			ViewProperty viewProperty = new ViewProperty();
			viewProperty.name = myProperty.name;
			viewProperty.capName = myProperty.capName;
			viewProperty.mappingPath = myProperty.getterName + "()";
			viewProperty.beanDataType = myProperty.beanDataType;
			viewProperty.dataType = myProperty.dataType;
			viewProperty.format = myProperty.format;
			viewProperty.nullable = myProperty.nullable;
			viewProperty.visibility = myProperty.visibility;
			viewProperty.editable = myProperty.editable;
			viewProperty.lastPropertyName = myProperty.name;
			viewProperty.lastColumnName = myProperty.column.name;
			viewProperty.joinedAliasName = "";				
			viewProperty.rendering = myProperty.rendering;
			result.add(viewProperty);
		} else {
			
			int limit = myProperty.embedded?myProperty.referenceBean.properties.size():myProperty.referenceBean.cardinality;
			
			for (int i = 0; i < limit; i++) {
				
				Property property = myProperty.referenceBean.properties.get(i);
				
				if (property.referenceBean != null) {
					List<ViewProperty> tempPropertyList = getViewProperties(property);
					for (ViewProperty tempProperty:tempPropertyList) {
						ViewProperty viewProperty = new ViewProperty();
						if (myProperty.embedded) {
							viewProperty.name = tempProperty.name;
							viewProperty.capName = tempProperty.capName;
						} else {
							viewProperty.name = myProperty.name + tempProperty.capName;
							viewProperty.capName = myProperty.capName + tempProperty.capName;
						}
						viewProperty.mappingPath = myProperty.getterName + "()." + tempProperty.mappingPath;
						viewProperty.beanDataType = tempProperty.beanDataType;
						viewProperty.dataType = tempProperty.dataType;
						viewProperty.format = tempProperty.format;
						viewProperty.nullable = myProperty.nullable || tempProperty.nullable;
						viewProperty.visibility = Visibility.min(myProperty.visibility, tempProperty.visibility);
						viewProperty.editable = myProperty.embedded?tempProperty.editable:myProperty.editable;
						viewProperty.lastPropertyName = tempProperty.lastPropertyName;
						viewProperty.lastColumnName = tempProperty.lastColumnName;
						viewProperty.joinedAliasName = myProperty.name + JavaClassNaming.getClassNameFromObjectName(tempProperty.joinedAliasName);
						viewProperty.selectableBean = tempProperty.selectableBean;					
						viewProperty.rendering = tempProperty.rendering;
						result.add(viewProperty);
					}
				} else {
					ViewProperty viewProperty = new ViewProperty();
					if (myProperty.embedded) {
						viewProperty.name = property.name;
						viewProperty.capName = property.capName;
					} else {
						viewProperty.name = myProperty.name + property.capName;
						viewProperty.capName = myProperty.capName + property.capName;
					}
					viewProperty.mappingPath = myProperty.getterName + "()." + property.getterName + "()";
					viewProperty.beanDataType = property.beanDataType;
					viewProperty.dataType = property.dataType;
					viewProperty.format = property.format;
					viewProperty.nullable = myProperty.nullable || property.nullable;
					viewProperty.visibility = Visibility.min(myProperty.visibility, property.visibility);
					viewProperty.editable = myProperty.embedded?property.editable:myProperty.editable;
					viewProperty.lastPropertyName = property.name;
					viewProperty.lastColumnName = property.column.name;
					viewProperty.joinedAliasName = myProperty.name;
					if (myProperty.referenceBean.selectable) {
						viewProperty.selectableBean = myProperty.referenceBean;
					}
					if (myProperty.referenceBean.cardinality == 1) {
						viewProperty.rendering = myProperty.rendering;
					} else {
						if (myProperty.embedded) {
							viewProperty.rendering = property.rendering;
						} else {
							viewProperty.rendering = myProperty.rendering + "(" + property.rendering + ")";
						}
					}
					
					result.add(viewProperty);
				}
			}
		}

		return result;
	}

	/**
	 * get the list of properties that will be used in bean views to show
	 * references to other beans
	 * 
	 * @return
	 */
	public List<ViewProperty> getReferenceProperties(Bean bean) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (int i = 0; i < bean.cardinality; i++) {
			Property property = bean.properties.get(i);
			result.addAll(property.viewProperties);
		}

		return result;

	}
	
	
	/**
	 * get the list of properties that will be available in a view bean
	 * 
	 * @return
	 */
	private List<ViewProperty> getViewProperties(Bean bean) {
		
		return getViewPropertiesExcludingField(bean, null);
	}
	
	
	/**
	 * get the list of properties that will be available in a view bean<br>
	 * Excludes a field name. Usefull for handling one to many views where we want to exclude reference to parent bean in a view
	 */
	private List<ViewProperty> getViewPropertiesExcludingField(Bean bean, String excludedFieldName) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (Property property:bean.properties) {
			if (!property.relation.isComponentLink() && !property.name.equals(excludedFieldName)) {
				result.addAll(property.viewProperties);
			}			
		}

		return result;
	}
	

	public List<ViewProperty> getBasicViewProperties(Bean bean) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (ViewProperty property:getViewProperties(bean)) {
			if (property.visibility.isListVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
	
	public List<ViewProperty> getFormProperties(Bean bean) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (ViewProperty property:getViewProperties(bean)) {
			if (property.visibility.isDetailVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
	
	
	/**
	 * get the list of properties that will be available in a basic view bean
	 * 
	 * @return
	 */
	private List<ViewProperty> getVisibleProperties(OneToMany oneToMany) {
		
		return getViewPropertiesExcludingField(oneToMany.referenceBean, oneToMany.referenceProperty.name);
	}
	
	public List<ViewProperty> getBasicViewProperties(OneToMany oneToMany) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (ViewProperty property:getVisibleProperties(oneToMany)) {
			if (property.visibility.isListVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
	
	public List<ViewProperty> getFormProperties(OneToMany oneToMany) {
		List<ViewProperty> result = new ArrayList<>();
		
		for (ViewProperty property:getVisibleProperties(oneToMany)) {
			if (property.visibility.isDetailVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
	
}
