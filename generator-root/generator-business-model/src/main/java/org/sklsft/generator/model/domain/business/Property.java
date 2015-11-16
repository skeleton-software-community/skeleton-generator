package org.sklsft.generator.model.domain.business;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.Format;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.Visibility;

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
	public List<String> annotations;

	public String joinedAliasName;
	public String lastPropertyName;

	public Bean comboBoxBean;

	public List<Property> getReferencePropertyList() {
		List<Property> referencePropertyList = new ArrayList<Property>();

		if (this.referenceBean != null) {
			List<Property> tempPropertyList = new ArrayList<Property>();
			
			int propertiesMaxIndex;
			if (referenceBean.cardinality > 0) {
				propertiesMaxIndex = referenceBean.cardinality;
			} else {
				propertiesMaxIndex = referenceBean.properties.size() - 1;
			}

			for (int i = 1; i <= propertiesMaxIndex; i++) {
				if (this.referenceBean.properties.get(i).referenceBean != null) {
					tempPropertyList = this.referenceBean.properties.get(i).getReferencePropertyList();
					for (int j = 0; j < tempPropertyList.size(); j++) {
						Property property = new Property();
						property.name = this.referenceBean.properties.get(i).name + tempPropertyList.get(j).capName;
						property.capName = this.referenceBean.properties.get(i).capName + tempPropertyList.get(j).capName;
						property.fetchName = this.referenceBean.properties.get(i).fetchName + "." + tempPropertyList.get(j).fetchName;
						property.beanDataType = tempPropertyList.get(j).beanDataType;
						property.dataType = tempPropertyList.get(j).dataType;
						property.format = tempPropertyList.get(j).format;
						property.nullable = this.referenceBean.properties.get(i).nullable;
						property.visibility = this.referenceBean.properties.get(i).visibility;
						property.editable = this.referenceBean.properties.get(i).editable;
						property.lastPropertyName = tempPropertyList.get(j).lastPropertyName;
						property.joinedAliasName = this.referenceBean.properties.get(i).capName + tempPropertyList.get(j).joinedAliasName;
						property.comboBoxBean = tempPropertyList.get(j).comboBoxBean;
						property.rendering = tempPropertyList.get(j).rendering;
						referencePropertyList.add(property);
					}
				} else {
					Property property = new Property();
					property.name = this.referenceBean.properties.get(i).name;
					property.capName = this.referenceBean.properties.get(i).capName;
					property.fetchName = this.referenceBean.properties.get(i).fetchName;
					property.beanDataType = this.referenceBean.properties.get(i).beanDataType;
					property.dataType = this.referenceBean.properties.get(i).dataType;
					property.format = this.referenceBean.properties.get(i).format;
					property.nullable = this.referenceBean.properties.get(i).nullable;
					property.visibility = this.referenceBean.properties.get(i).visibility;
					property.editable = this.referenceBean.properties.get(i).editable;
					property.lastPropertyName = this.referenceBean.properties.get(i).name;
					property.joinedAliasName = "";

					if (this.referenceBean.cardinality == 1) {
						property.rendering = this.rendering;
					} else {
						property.rendering = this.rendering + "(" + this.referenceBean.properties.get(i).rendering + ")";
					}
					if (this.referenceBean.hasComboBox) {
						property.comboBoxBean = this.referenceBean;
					}
					referencePropertyList.add(property);
				}
			}
		}

		return referencePropertyList;

	}
}
