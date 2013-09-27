package com.skeleton.generator.model.om;

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
	public String baseStateManagerInterfaceName;
	public String stateManagerInterfaceName;
	public String stateManagerObjectName;

	public String baseMapperClassName;
	public String mapperClassName;
	public String baseMapperInterfaceName;
	public String mapperInterfaceName;
	public String mapperObjectName;

	public String baseControllerClassName;
	public String controllerClassName;
	public String controllerObjectName;

	public int cardinality;
	public String listRendering;
	public String detailRendering;
	public boolean hasComboBox;
	public boolean createEnabled;
	public boolean updateEnabled;
	public boolean deleteEnabled;
	public String interfaces;
	public String annotations;

	public List<Property> propertyList;
	public List<OneToMany> oneToManyList;
	public List<OneToManyComponent> oneToManyComponentList;
	public List<OneToOne> oneToOneList;
	public List<OneToOneComponent> oneToOneComponentList;
	public List<UniqueComponent> uniqueComponentList;

	public boolean isComponent = false;
	public boolean hasStatus = false;

	/**
	 * get the list of properties that will be used in bean views to show
	 * references to other beans
	 * 
	 * @return
	 */
	public List<Property> getFindPropertyList() {
		List<Property> findPropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.propertyList.get(i).referenceBean != null) {
				tempPropertyList = this.propertyList.get(i).referenceBean.getFindPropertyList();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property property = new Property();
					property.name = propertyList.get(i).name + tempPropertyList.get(j).capName;
					property.capName = propertyList.get(i).capName + tempPropertyList.get(j).capName;
					property.fetchName = propertyList.get(i).fetchName + "." + tempPropertyList.get(j).fetchName;
					property.beanDataType = tempPropertyList.get(j).beanDataType;
					property.dataType = tempPropertyList.get(j).dataType;
					property.format = tempPropertyList.get(j).format;
					property.nullable = this.propertyList.get(i).nullable;
					property.listVisible = this.propertyList.get(i).listVisible;
					property.detailVisible = this.propertyList.get(i).detailVisible;
					property.editable = this.propertyList.get(i).editable;
					property.lastPropertyName = tempPropertyList.get(j).lastPropertyName;
					property.joinedAliasName = propertyList.get(i).capName + tempPropertyList.get(j).joinedAliasName;
					property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
					property.rendering = tempPropertyList.get(j).rendering;
					findPropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = propertyList.get(i).name;
				property.capName = propertyList.get(i).capName;
				property.fetchName = propertyList.get(i).fetchName;
				property.beanDataType = propertyList.get(i).beanDataType;
				property.dataType = propertyList.get(i).dataType;
				property.format = propertyList.get(i).format;
				property.nullable = this.propertyList.get(i).nullable;
				property.listVisible = this.propertyList.get(i).listVisible;
				property.detailVisible = this.propertyList.get(i).detailVisible;
				property.editable = this.propertyList.get(i).editable;
				property.lastPropertyName = propertyList.get(i).name;
				property.joinedAliasName = "";
				property.rendering = propertyList.get(i).rendering;
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
	public List<Property> getVisiblePropertyList() {
		List<Property> visiblePropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i < this.propertyList.size(); i++) {
			if (this.propertyList.get(i).referenceBean != null) {
				tempPropertyList = this.propertyList.get(i).referenceBean.getFindPropertyList();
				for (int j = 0; j < tempPropertyList.size(); j++) {
					Property property = new Property();
					property.name = propertyList.get(i).name + tempPropertyList.get(j).capName;
					property.capName = propertyList.get(i).capName + tempPropertyList.get(j).capName;
					property.beanDataType = tempPropertyList.get(j).beanDataType;
					property.dataType = tempPropertyList.get(j).dataType;
					property.format = tempPropertyList.get(j).format;
					property.nullable = this.propertyList.get(i).nullable;
					property.listVisible = this.propertyList.get(i).listVisible;
					property.detailVisible = this.propertyList.get(i).detailVisible;
					property.editable = this.propertyList.get(i).editable;
					property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
					property.rendering = tempPropertyList.get(j).rendering;
					visiblePropertyList.add(property);
				}
			} else {
				Property property = new Property();
				property.name = propertyList.get(i).name;
				property.capName = propertyList.get(i).capName;
				property.beanDataType = propertyList.get(i).beanDataType;
				property.dataType = propertyList.get(i).dataType;
				property.format = propertyList.get(i).format;
				property.nullable = this.propertyList.get(i).nullable;
				property.listVisible = this.propertyList.get(i).listVisible;
				property.detailVisible = this.propertyList.get(i).detailVisible;
				property.editable = this.propertyList.get(i).editable;
				property.rendering = propertyList.get(i).rendering;
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
	public List<Alias> getFindAliasList() {
		List<Alias> aliasList = new ArrayList<Alias>();
		List<Alias> tempAliasList = new ArrayList<Alias>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.propertyList.get(i).referenceBean != null) {
				Alias alias = new Alias();
				alias.propertyName = propertyList.get(i).name;
				alias.name = propertyList.get(i).capName;
				aliasList.add(alias);

				tempAliasList = this.propertyList.get(i).referenceBean.getFindAliasList();
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
