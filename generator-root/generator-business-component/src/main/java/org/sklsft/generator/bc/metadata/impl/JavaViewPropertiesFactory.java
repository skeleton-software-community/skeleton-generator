package org.sklsft.generator.bc.metadata.impl;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.bc.metadata.interfaces.ViewPropertiesFactory;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.business.Property;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.Visibility;
import org.springframework.stereotype.Component;

@Component("javaViewPropertiesFactory")
public class JavaViewPropertiesFactory implements ViewPropertiesFactory {

	/**
	 * get the list of properties that will be used in bean views to show
	 * references to other beans
	 * 
	 * @return
	 */
	public List<ViewProperty> getReferenceProperties(Bean bean) {
		List<ViewProperty> result = new ArrayList<>();
		List<ViewProperty> tempPropertyList = new ArrayList<>();
		
		for (int i = 0; i < bean.cardinality; i++) {
			Property currentProperty = bean.properties.get(i);
			if (currentProperty.referenceBean != null) {
				tempPropertyList = getReferenceProperties(currentProperty.referenceBean);
				for (ViewProperty tempProperty:tempPropertyList) {
					ViewProperty viewProperty = new ViewProperty();
					viewProperty.name = currentProperty.name + tempProperty.capName;
					viewProperty.capName = currentProperty.capName + tempProperty.capName;
					viewProperty.mappingPath = currentProperty.getterName + "()." + tempProperty.mappingPath;
					viewProperty.beanDataType = tempProperty.beanDataType;
					viewProperty.dataType = tempProperty.dataType;
					viewProperty.format = tempProperty.format;
					viewProperty.nullable = currentProperty.nullable;
					viewProperty.visibility = currentProperty.visibility;
					viewProperty.editable = currentProperty.editable;
					viewProperty.lastPropertyName = tempProperty.lastPropertyName;
					viewProperty.joinedAliasName = currentProperty.capName + tempProperty.joinedAliasName;
					viewProperty.comboBoxBean = tempProperty.comboBoxBean;
					
					result.add(viewProperty);
				}
			} else {
				ViewProperty viewProperty = new ViewProperty();
				viewProperty.name = currentProperty.name;
				viewProperty.capName = currentProperty.capName;
				viewProperty.mappingPath = currentProperty.getterName + "()";
				viewProperty.beanDataType = currentProperty.beanDataType;
				viewProperty.dataType = currentProperty.dataType;
				viewProperty.format = currentProperty.format;
				viewProperty.nullable = currentProperty.nullable;
				viewProperty.visibility = currentProperty.visibility;
				viewProperty.editable = currentProperty.editable;
				viewProperty.lastPropertyName = currentProperty.name;
				viewProperty.joinedAliasName = "";
				if (bean.hasComboBox) {
					viewProperty.comboBoxBean = bean;
				}
				
				result.add(viewProperty);
			}
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
		
		for (Property currentProperty:bean.properties) {
			if (currentProperty.referenceBean != null) {
				if (!currentProperty.relation.isComponentLink() && !currentProperty.name.equals(excludedFieldName)) {
					if (currentProperty.referenceBean.isEmbedded) {
						addEmbeddedProperties(currentProperty, result);
					} else {
						addReferenceProperties(currentProperty, result);
					}
				}				
			} else {
				ViewProperty viewProperty = new ViewProperty();
				viewProperty.name = currentProperty.name;
				viewProperty.capName = currentProperty.capName;
				viewProperty.beanDataType = currentProperty.beanDataType;
				viewProperty.dataType = currentProperty.dataType;
				viewProperty.format = currentProperty.format;
				viewProperty.nullable = currentProperty.nullable;
				viewProperty.visibility = currentProperty.visibility;
				viewProperty.editable = currentProperty.editable;

				result.add(viewProperty);
			}
		}

		return result;
	}
	
	private void addReferenceProperties(Property currentProperty, List<ViewProperty> visiblePropertyList) {
		List<ViewProperty> tempPropertyList = getReferenceProperties(currentProperty.referenceBean);
		for (ViewProperty tempProperty:tempPropertyList) {
			ViewProperty viewProperty = new ViewProperty();
			viewProperty.name = currentProperty.name + tempProperty.capName;
			viewProperty.capName = currentProperty.capName + tempProperty.capName;
			viewProperty.beanDataType = tempProperty.beanDataType;
			viewProperty.dataType = tempProperty.dataType;
			viewProperty.format = tempProperty.format;
			viewProperty.nullable = currentProperty.nullable;
			viewProperty.visibility = currentProperty.visibility;
			viewProperty.editable = currentProperty.editable;
			viewProperty.comboBoxBean = tempProperty.comboBoxBean;

			visiblePropertyList.add(viewProperty);
		}		
	}
	
	private void addEmbeddedProperties(Property currentProperty, List<ViewProperty> visiblePropertyList) {
		List<ViewProperty> tempPropertyList = getViewProperties(currentProperty.referenceBean);
		for (ViewProperty tempProperty:tempPropertyList) {
			ViewProperty viewProperty = new ViewProperty();
			viewProperty.name = tempProperty.name;
			viewProperty.capName = tempProperty.capName;
			viewProperty.beanDataType = tempProperty.beanDataType;
			viewProperty.dataType = tempProperty.dataType;
			viewProperty.format = tempProperty.format;
			viewProperty.nullable = tempProperty.nullable;
			viewProperty.visibility = Visibility.min(currentProperty.visibility, tempProperty.visibility);
			viewProperty.editable = tempProperty.editable;
			viewProperty.comboBoxBean = tempProperty.comboBoxBean;

			visiblePropertyList.add(viewProperty);
		}		
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
