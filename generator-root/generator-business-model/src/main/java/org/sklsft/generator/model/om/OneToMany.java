package org.sklsft.generator.model.om;

import java.util.ArrayList;
import java.util.List;

public class OneToMany {

	public Bean referenceBean;
    public Property referenceProperty;
    public Bean parentBean;
    public String collectionName;
    public String collectionGetterName;
    public String collectionSetterName;
    
    
	/**
	 * get the list of properties that will be available in a basic view bean
	 * 
	 * @return
	 */
	public List<Property> getVisibleProperties() {
		List<Property> visiblePropertyList = new ArrayList<Property>();
		List<Property> tempPropertyList = new ArrayList<Property>();

		for (int i = 1; i < referenceBean.properties.size(); i++) {
			Property currentProperty = referenceBean.properties.get(i);
			if (currentProperty.referenceBean != null) {
				if (!currentProperty.name.equals(referenceProperty.name)) {
					tempPropertyList = currentProperty.referenceBean.getFindProperties();
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
}
