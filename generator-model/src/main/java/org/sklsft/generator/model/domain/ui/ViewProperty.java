package org.sklsft.generator.model.domain.ui;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.model.metadata.TextFilterType;
import org.sklsft.generator.model.metadata.Visibility;

public class ViewProperty {

	public String name;
	public String capName;
	public String getterName;
	public String setterName;
	public DataType dataType;
	public String javaType;
	public String tsType;
	public boolean nullable;
	public boolean editable;
	public boolean filterable;
	public TextFilterType textFilterType;
	public Visibility visibility;
	public String rendering;
	
	public Bean referenceBean;
	public Bean selectableBean;
	
	public String mappingPath;
	
	public String joinedAliasName;
	public String lastParentBeanClassName;
	public String lastPropertyName;
	
	
	public boolean isComboboxable( ) {
		return selectableBean!=null && selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS);
	}	
}
