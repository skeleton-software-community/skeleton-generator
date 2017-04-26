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
	
	public String beanDataType;
	public DataType dataType;
	public boolean nullable;
	public boolean unique;
	public Bean referenceBean;
	public RelationType relation;
	public boolean embedded;
	public Format format;
	public boolean editable;
	public Visibility visibility;
	public String rendering;
	public List<String> annotations;

	

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
				
				Property currentProperty = this.referenceBean.properties.get(i);
				
				if (currentProperty.referenceBean != null) {
					tempPropertyList = currentProperty.getReferencePropertyList();
					for (Property tempProperty:tempPropertyList) {
						Property property = new Property();
						property.name = currentProperty.name + tempProperty.capName;
						property.capName = currentProperty.capName + tempProperty.capName;
						
						property.rendering = tempProperty.rendering;
						referencePropertyList.add(property);
					}
				} else {
					Property property = new Property();
					property.name = currentProperty.name;
					property.capName = currentProperty.capName;

					if (this.referenceBean.cardinality == 1) {
						property.rendering = this.rendering;
					} else {
						if (this.referenceBean.isEmbedded) {
							property.rendering = currentProperty.rendering;
						} else {
							property.rendering = this.rendering + "(" + currentProperty.rendering + ")";
						}
					}
					
					referencePropertyList.add(property);
				}
			}
		}

		return referencePropertyList;

	}
}
