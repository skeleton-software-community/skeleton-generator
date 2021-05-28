package org.sklsft.generator.components.metadata.factory.impl;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.components.metadata.factory.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.Visibility;
import org.sklsft.generator.model.util.naming.JavaClassNaming;
import org.springframework.stereotype.Component;

@Component("javaViewPropertiesFactory")
public class JavaViewPropertiesFactory implements ViewPropertiesFactory {

	private List<ViewProperty> getViewProperties(Property property, Bean bean) {

		List<ViewProperty> result = new ArrayList<>();
		
		if (property.referenceBean == null) {
			ViewProperty viewProperty = new ViewProperty();
			viewProperty.name = property.name;
			viewProperty.capName = property.capName;
			viewProperty.mappingPath = property.getterName + "()";
			viewProperty.javaType = property.javaType;
			viewProperty.tsType = property.tsType;
			viewProperty.dataType = property.dataType;
			viewProperty.nullable = property.nullable;
			viewProperty.visibility = property.visibility;
			viewProperty.editable = property.editable;
			viewProperty.filterable = property.filterable;
			viewProperty.lastPropertyName = property.name;
			viewProperty.lastParentBeanClassName = bean.className;
			viewProperty.joinedAliasName = "";				
			viewProperty.rendering = property.rendering;
			result.add(viewProperty);
		} else {
			
			int limit = property.embedded?property.referenceBean.properties.size():property.referenceBean.cardinality;
			
			for (int i = 0; i < limit; i++) {
				
				Property referenceBeanProperty = property.referenceBean.properties.get(i);
				
				if (referenceBeanProperty.referenceBean != null) {
					List<ViewProperty> tempPropertyList = getViewProperties(referenceBeanProperty, property.referenceBean);
					for (ViewProperty tempProperty:tempPropertyList) {
						ViewProperty viewProperty = new ViewProperty();
						if (property.embedded) {
							viewProperty.name = tempProperty.name;
							viewProperty.capName = tempProperty.capName;
						} else {
							viewProperty.name = property.name + tempProperty.capName;
							viewProperty.capName = property.capName + tempProperty.capName;
						}
						viewProperty.mappingPath = property.getterName + "()." + tempProperty.mappingPath;
						viewProperty.javaType = tempProperty.javaType;
						viewProperty.tsType = tempProperty.tsType;
						viewProperty.dataType = tempProperty.dataType;
						viewProperty.nullable = property.nullable || tempProperty.nullable;
						viewProperty.visibility = Visibility.min(property.visibility, tempProperty.visibility);
						viewProperty.editable = property.embedded?tempProperty.editable:property.editable;
						viewProperty.filterable = property.embedded?tempProperty.filterable:property.filterable;
						viewProperty.lastPropertyName = tempProperty.lastPropertyName;
						viewProperty.lastParentBeanClassName = tempProperty.lastParentBeanClassName;
						viewProperty.joinedAliasName = property.name + JavaClassNaming.getClassNameFromObjectName(tempProperty.joinedAliasName);
						viewProperty.referenceBean = tempProperty.referenceBean;
						viewProperty.selectableBean = tempProperty.selectableBean;					
						viewProperty.rendering = tempProperty.rendering;
						result.add(viewProperty);
					}
				} else {
					ViewProperty viewProperty = new ViewProperty();
					if (property.embedded) {
						viewProperty.name = referenceBeanProperty.name;
						viewProperty.capName = referenceBeanProperty.capName;
					} else {
						viewProperty.name = property.name + referenceBeanProperty.capName;
						viewProperty.capName = property.capName + referenceBeanProperty.capName;
					}
					viewProperty.mappingPath = property.getterName + "()." + referenceBeanProperty.getterName + "()";
					viewProperty.javaType = referenceBeanProperty.javaType;
					viewProperty.tsType = referenceBeanProperty.tsType;
					viewProperty.dataType = referenceBeanProperty.dataType;
					viewProperty.nullable = property.nullable || referenceBeanProperty.nullable;
					viewProperty.visibility = Visibility.min(property.visibility, referenceBeanProperty.visibility);
					viewProperty.editable = property.embedded?referenceBeanProperty.editable:property.editable;
					viewProperty.filterable = property.embedded?referenceBeanProperty.filterable:property.filterable;
					viewProperty.lastPropertyName = referenceBeanProperty.name;
					viewProperty.lastParentBeanClassName = property.javaType;
					viewProperty.joinedAliasName = property.name;
					viewProperty.referenceBean = property.referenceBean;
					if (property.referenceBean.selectable) {
						viewProperty.selectableBean = property.referenceBean;
					}
					if (property.referenceBean.cardinality == 1) {
						viewProperty.rendering = property.rendering;
					} else {
						if (property.embedded) {
							viewProperty.rendering = referenceBeanProperty.rendering;
						} else {
							viewProperty.rendering = property.rendering + "(" + referenceBeanProperty.rendering + ")";
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
			result.addAll(getViewProperties(property, bean));
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
				result.addAll(getViewProperties(property, bean));
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
