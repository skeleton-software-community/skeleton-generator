package org.skeleton.generator.model.om;

import org.skeleton.generator.util.metadata.DataType;
import org.skeleton.generator.util.metadata.Format;
import org.skeleton.generator.util.metadata.RelationType;

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
