package org.sklsft.generator.model.domain.business;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.domain.ui.BasicViewBean;
import org.sklsft.generator.model.domain.ui.FullViewBean;
import org.sklsft.generator.model.domain.ui.OptionBean;

/**
 * representation of a bean<br/>
 * Properties are willingly public because of their intensive use in file write
 * commands<br/>
 * The cardinality of a bean represents the number of fields that are needed to
 * build the business key (and its associated unique constraint)
 * 
 * @author Nicolas Thibault
 * 
 */
public class Bean {

	public Table table;
	public Package myPackage;
	public String className;
	public String objectName;

	public String baseDaoClassName;
	public String daoClassName;
	public String baseDaoInterfaceName;
	public String daoInterfaceName;
	public String daoObjectName;

	public String baseServiceClassName;
	public String serviceClassName;
	public String baseServiceInterfaceName;
	public String serviceInterfaceName;
	public String serviceObjectName;

	public String baseStateManagerClassName;
	public String stateManagerClassName;
	public String stateManagerObjectName;
	
	public String baseProcessorClassName;
	public String processorClassName;
	public String processorObjectName;

	public String baseListControllerClassName;
	public String baseDetailControllerClassName;
	public String listControllerClassName;
	public String listControllerObjectName;
	public String detailControllerClassName;
	public String detailControllerObjectName;
	
	public String detailViewClassName;
	public String listViewClassName;
	public String detailViewObjectName;
	public String listViewObjectName;
	
	public int cardinality;
	public String listRendering;
	public String detailRendering;
	public boolean hasComboBox;
	public boolean createEnabled;
	public boolean updateEnabled;
	public boolean deleteEnabled;
	public List<String> interfaces;
	public List<String> annotations;

	public List<Property> properties;
	public List<OneToMany> oneToManyList;
	public List<OneToManyComponent> oneToManyComponentList;
	public List<OneToOne> oneToOneList;
	public List<UniqueComponent> uniqueComponentList;

	public boolean isComponent = false;
	public boolean isManyToOneComponent = false;
	public boolean isUniqueComponent = false;
	
	public BasicViewBean basicViewBean;
	public FullViewBean fullViewBean;
	public OptionBean optionBean;

	/**
	 * get the list of properties that will be used in bean views to show
	 * references to other beans
	 * 
	 * @return
	 */
	public List<Property> getReferenceProperties() {
		List<Property> findPropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();
		
		int propertiesMaxIndex;
		if (this.cardinality > 0) {
			propertiesMaxIndex = this.cardinality;
		} else {
			propertiesMaxIndex = this.properties.size() - 1;
		}

		for (int i = 1; i <= propertiesMaxIndex; i++) {
			Property currentProperty = this.properties.get(i);
			if (currentProperty.referenceBean != null) {
				tempPropertyList = currentProperty.referenceBean.getReferenceProperties();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property tempProperty = tempPropertyList.get(j);
					Property property = new Property();
					property.name = currentProperty.name + tempProperty.capName;
					property.capName = currentProperty.capName + tempProperty.capName;
					property.fetchName = currentProperty.fetchName + "." + tempProperty.fetchName;
					property.beanDataType = tempProperty.beanDataType;
					property.dataType = tempProperty.dataType;
					property.format = tempProperty.format;
					property.nullable = currentProperty.nullable;
					property.visibility = currentProperty.visibility;
					property.editable = currentProperty.editable;
					property.lastPropertyName = tempProperty.lastPropertyName;
					property.joinedAliasName = currentProperty.capName + tempProperty.joinedAliasName;
					property.comboBoxBean = tempProperty.comboBoxBean;
					property.rendering = tempProperty.rendering;
					findPropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = currentProperty.name;
				property.capName = currentProperty.capName;
				property.fetchName = currentProperty.fetchName;
				property.beanDataType = currentProperty.beanDataType;
				property.dataType = currentProperty.dataType;
				property.format = currentProperty.format;
				property.nullable = currentProperty.nullable;
				property.visibility = currentProperty.visibility;
				property.editable = currentProperty.editable;
				property.lastPropertyName = currentProperty.name;
				property.joinedAliasName = "";
				property.rendering = currentProperty.rendering;
				if (hasComboBox) {
					property.comboBoxBean = this;
				}
				findPropertyList.add(property);
			}
		}

		return findPropertyList;

	}

	/**
	 * get the list of properties that will be available in a view bean
	 * 
	 * @return
	 */
	private List<Property> getVisibleProperties() {
		List<Property> visiblePropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i < this.properties.size(); i++) {
			Property currentProperty = this.properties.get(i);
			if (currentProperty.referenceBean != null) {
				tempPropertyList = currentProperty.referenceBean.getReferenceProperties();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property tempProperty = tempPropertyList.get(j);
					Property property = new Property();
					property.name = currentProperty.name + tempProperty.capName;
					property.capName = currentProperty.capName + tempProperty.capName;
					property.beanDataType = tempProperty.beanDataType;
					property.dataType = tempProperty.dataType;
					property.format = tempProperty.format;
					property.nullable = currentProperty.nullable;
					property.visibility = currentProperty.visibility;
					property.editable = currentProperty.editable;
					property.comboBoxBean = tempProperty.comboBoxBean;
					property.rendering = tempProperty.rendering;
					visiblePropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = currentProperty.name;
				property.capName = currentProperty.capName;
				property.beanDataType = currentProperty.beanDataType;
				property.dataType = currentProperty.dataType;
				property.format = currentProperty.format;
				property.nullable = currentProperty.nullable;
				property.visibility = currentProperty.visibility;
				property.editable = currentProperty.editable;
				property.rendering = currentProperty.rendering;
				visiblePropertyList.add(property);
			}
		}

		return visiblePropertyList;
	}

	/**
	 * get the list of aliases that will be used to build queries
	 * 
	 * @return
	 */
	public List<Alias> getReferenceAliases() {
		List<Alias> aliasList = new ArrayList<Alias>();
		List<Alias> tempAliasList = new ArrayList<Alias>();

		for (int i = 1; i <= this.cardinality; i++) {
			Property currentProperty = this.properties.get(i);
			if (currentProperty.referenceBean != null) {
				Alias alias = new Alias();
				alias.propertyName = currentProperty.name;
				alias.name = currentProperty.capName;
				aliasList.add(alias);

				tempAliasList = currentProperty.referenceBean.getReferenceAliases();
				for (int j = 0; j < tempAliasList.size(); j++) {
					Alias currentAlias = tempAliasList.get(j);
					Alias tempAlias = new Alias();
					tempAlias.propertyName = alias.propertyName + "." + currentAlias.propertyName;
					tempAlias.name = alias.name + currentAlias.name;
					aliasList.add(tempAlias);
				}
			}
		}

		return aliasList;
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
	
	public List<Property> getFullViewProperties() {
		List<Property> result = new ArrayList<>();
		
		for (Property property:getVisibleProperties()) {
			if (property.visibility.isDetailVisible()) {
				result.add(property);
			}
		}
		
		return result;
	}
}
