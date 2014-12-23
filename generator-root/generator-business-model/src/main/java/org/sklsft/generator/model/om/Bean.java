package org.sklsft.generator.model.om;

import java.util.ArrayList;
import java.util.List;

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
	public String viewClassName;
	public String viewObjectName;

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

	public String baseMapperClassName;
	public String mapperClassName;
	public String mapperObjectName;

	public String baseControllerClassName;
	public String controllerClassName;
	public String controllerObjectName;
	
	public String filterClassName;
	public String filterObjectName;

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
	public boolean hasStatus = false;
	public boolean isManyToOneComponent = false;

	/**
	 * get the list of properties that will be used in bean views to show
	 * references to other beans
	 * 
	 * @return
	 */
	public List<Property> getFindProperties() {
		List<Property> findPropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.properties.get(i).referenceBean != null) {
				tempPropertyList = this.properties.get(i).referenceBean.getFindProperties();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property property = new Property();
					property.name = properties.get(i).name + tempPropertyList.get(j).capName;
					property.capName = properties.get(i).capName + tempPropertyList.get(j).capName;
					property.fetchName = properties.get(i).fetchName + "." + tempPropertyList.get(j).fetchName;
					property.beanDataType = tempPropertyList.get(j).beanDataType;
					property.dataType = tempPropertyList.get(j).dataType;
					property.format = tempPropertyList.get(j).format;
					property.nullable = this.properties.get(i).nullable;
					property.visibility = this.properties.get(i).visibility;
					property.editable = this.properties.get(i).editable;
					property.lastPropertyName = tempPropertyList.get(j).lastPropertyName;
					property.joinedAliasName = properties.get(i).capName + tempPropertyList.get(j).joinedAliasName;
					property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
					property.rendering = tempPropertyList.get(j).rendering;
					findPropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = properties.get(i).name;
				property.capName = properties.get(i).capName;
				property.fetchName = properties.get(i).fetchName;
				property.beanDataType = properties.get(i).beanDataType;
				property.dataType = properties.get(i).dataType;
				property.format = properties.get(i).format;
				property.nullable = this.properties.get(i).nullable;
				property.visibility = this.properties.get(i).visibility;
				property.editable = this.properties.get(i).editable;
				property.lastPropertyName = properties.get(i).name;
				property.joinedAliasName = "";
				property.rendering = properties.get(i).rendering;
				if (hasComboBox) {
					property.comboBoxBean = this;
				}
				findPropertyList.add(property);
			}
		}

		return findPropertyList;

	}

	/**
	 * get the list of properties that will be available in a basic view bean
	 * 
	 * @return
	 */
	public List<Property> getVisibleProperties() {
		List<Property> visiblePropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i < this.properties.size(); i++) {
			if (this.properties.get(i).referenceBean != null) {
				tempPropertyList = this.properties.get(i).referenceBean.getFindProperties();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property property = new Property();
					property.name = properties.get(i).name + tempPropertyList.get(j).capName;
					property.capName = properties.get(i).capName + tempPropertyList.get(j).capName;
					property.beanDataType = tempPropertyList.get(j).beanDataType;
					property.dataType = tempPropertyList.get(j).dataType;
					property.format = tempPropertyList.get(j).format;
					property.nullable = this.properties.get(i).nullable;
					property.visibility = this.properties.get(i).visibility;
					property.editable = this.properties.get(i).editable;
					property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
					property.rendering = tempPropertyList.get(j).rendering;
					visiblePropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = properties.get(i).name;
				property.capName = properties.get(i).capName;
				property.beanDataType = properties.get(i).beanDataType;
				property.dataType = properties.get(i).dataType;
				property.format = properties.get(i).format;
				property.nullable = this.properties.get(i).nullable;
				property.visibility = this.properties.get(i).visibility;
				property.editable = this.properties.get(i).editable;
				property.rendering = properties.get(i).rendering;
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
	public List<Alias> getFindAliases() {
		List<Alias> aliasList = new ArrayList<Alias>();
		List<Alias> tempAliasList = new ArrayList<Alias>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.properties.get(i).referenceBean != null) {
				Alias alias = new Alias();
				alias.propertyName = properties.get(i).name;
				alias.name = properties.get(i).capName;
				aliasList.add(alias);

				tempAliasList = this.properties.get(i).referenceBean.getFindAliases();
				for (int j = 0; j < tempAliasList.size(); j++) {
					Alias tempAlias = new Alias();
					tempAlias.propertyName = alias.propertyName + "." + tempAliasList.get(j).propertyName;
					tempAlias.name = alias.name + tempAliasList.get(j).name;
					aliasList.add(tempAlias);
				}
			}
		}

		return aliasList;
	}

	/**
	 * determines whether the bean has embedded beans or not
	 * 
	 * @return
	 */
	public boolean isSimple() {
		return (this.oneToManyComponentList.size() <= 0 && this.uniqueComponentList.size() <= 0);
	}
}
