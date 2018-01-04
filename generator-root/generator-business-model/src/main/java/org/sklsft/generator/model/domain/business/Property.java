package org.sklsft.generator.model.domain.business;

import java.util.List;

import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.ui.ViewProperty;
import org.sklsft.generator.model.metadata.DataType;
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
	public boolean editable;
	public Visibility visibility;
	public String rendering;
	public List<String> annotations;
	
	public List<ViewProperty> viewProperties;

}
