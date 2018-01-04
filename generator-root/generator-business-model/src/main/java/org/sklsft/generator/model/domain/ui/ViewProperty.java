package org.sklsft.generator.model.domain.ui;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.SelectionMode;
import org.sklsft.generator.model.metadata.Visibility;

public class ViewProperty {

	public String name;
	public String capName;
	public String getterName;
	public String setterName;
	public DataType dataType;
	public String beanDataType;
	public boolean nullable;
	public boolean editable;
	public Visibility visibility;
	public String rendering;
	
	public Bean selectableBean;
	
	public String mappingPath;
	
	public String joinedAliasName;
	public String lastPropertyName;
	public String lastColumnName;
	
	
	public boolean isComboboxable( ) {
		return selectableBean!=null && selectableBean.selectionBehavior.selectionMode.equals(SelectionMode.DROPDOWN_OPTIONS);
	}	
}
