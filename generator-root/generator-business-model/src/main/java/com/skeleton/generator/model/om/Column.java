package com.skeleton.generator.model.om;

import com.skeleton.generator.model.enumeration.DataType;
import com.skeleton.generator.model.enumeration.Format;
import com.skeleton.generator.model.enumeration.RelationType;

public class Column {
	
	public String name;
    public String originalName;
    public DataType dataType;
    public boolean nullable;
    public boolean unique;
    public Table referenceTable;
    public RelationType relation;
    public boolean deleteCascade;

    public Format format;
    public boolean editable;
    public boolean listVisible;
    public boolean detailVisible;
    public String rendering;

}
