package org.sklsft.generator.model.domain.ui;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.Format;
import org.sklsft.generator.model.metadata.Visibility;

public class ViewProperty {

	public String name;
	public String capName;
	public String getterName;
	public String setterName;
	public DataType dataType;
	public String beanDataType;
	public boolean nullable;
	public Format format;
	public boolean editable;
	public Visibility visibility;
	public String rendering;
	
	public Bean comboBoxBean;
	
	public String mappingPath;
	
	public String joinedAliasName;
	public String lastPropertyName;
	
}
