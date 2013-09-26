package com.skeleton.generator.model.om;

import com.skeleton.generator.model.enumeration.DataType;
import com.skeleton.generator.model.enumeration.Format;
import com.skeleton.generator.model.enumeration.RelationType;

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
    public boolean listVisible;
    public boolean detailVisible;
    public String rendering;
    
    public String joinedAliasName;
    public String lastPropertyName;

    public Bean comboBoxBean;
}
