package org.skeleton.generator.model.om;

import java.util.ArrayList;
import java.util.List;

import org.skeleton.generator.util.metadata.DataType;
import org.skeleton.generator.util.metadata.Format;
import org.skeleton.generator.util.metadata.RelationType;
import org.skeleton.generator.util.metadata.Visibility;

public class Property {

	public Column column;
	public String name;
	public String capName;
	public String getterName;
	public String setterName;
	public String fetchName;
	public String beanDataType;
	public DataType dataType;
	public boolean nullable;
	public boolean unique;
	public Bean referenceBean;
	public RelationType relation;
	public Format format;
	public boolean editable;
	public Visibility visibility;
	public String rendering;

	public String joinedAliasName;
	public String lastPropertyName;

	public Bean comboBoxBean;

	public List<Property> getFindPropertyList() {
		List<Property> findPropertyList = new ArrayList<Property>();

		if (this.referenceBean != null) {
			List<Property> tempPropertyList = new ArrayList<Property>();

			for (int i = 1; i <= this.referenceBean.cardinality; i++) {
				if (this.referenceBean.propertyList.get(i).referenceBean != null) {
					tempPropertyList = this.referenceBean.propertyList.get(i).getFindPropertyList();
					for (int j = 0; j < tempPropertyList.size(); j++) {
						Property property = new Property();
						property.name = this.referenceBean.propertyList.get(i).name + tempPropertyList.get(j).capName;
						property.capName = this.referenceBean.propertyList.get(i).capName + tempPropertyList.get(j).capName;
						property.fetchName = this.referenceBean.propertyList.get(i).fetchName + "." + tempPropertyList.get(j).fetchName;
						property.beanDataType = tempPropertyList.get(j).beanDataType;
						property.dataType = tempPropertyList.get(j).dataType;
						property.format = tempPropertyList.get(j).format;
						property.nullable = this.referenceBean.propertyList.get(i).nullable;
						property.visibility = this.referenceBean.propertyList.get(i).visibility;
						property.editable = this.referenceBean.propertyList.get(i).editable;
						property.lastPropertyName = tempPropertyList.get(j).lastPropertyName;
						property.joinedAliasName = this.referenceBean.propertyList.get(i).capName + tempPropertyList.get(j).joinedAliasName;
						property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
						property.rendering = tempPropertyList.get(j).rendering;
						findPropertyList.add(property);
					}
				} else {
					Property property = new Property();
					property.name = this.referenceBean.propertyList.get(i).name;
					property.capName = this.referenceBean.propertyList.get(i).capName;
					property.fetchName = this.referenceBean.propertyList.get(i).fetchName;
					property.beanDataType = this.referenceBean.propertyList.get(i).beanDataType;
					property.dataType = this.referenceBean.propertyList.get(i).dataType;
					property.format = this.referenceBean.propertyList.get(i).format;
					property.nullable = this.referenceBean.propertyList.get(i).nullable;
					property.visibility = this.referenceBean.propertyList.get(i).visibility;
					property.editable = this.referenceBean.propertyList.get(i).editable;
					property.lastPropertyName = this.referenceBean.propertyList.get(i).name;
					property.joinedAliasName = "";

					if (this.referenceBean.cardinality == 1) {
						property.rendering = this.rendering;
					} else {
						property.rendering = this.rendering + "(" + this.referenceBean.propertyList.get(i).rendering + ")";
					}
					if (this.referenceBean.hasComboBox) {
						property.comboBoxBean = this.referenceBean;
					}
					findPropertyList.add(property);
				}
			}
		}

		return findPropertyList;

	}
}
