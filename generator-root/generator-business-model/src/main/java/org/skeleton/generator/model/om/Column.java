package org.skeleton.generator.model.om;

import java.util.List;

import org.skeleton.generator.model.metadata.DataType;
import org.skeleton.generator.model.metadata.Format;
import org.skeleton.generator.model.metadata.RelationType;
import org.skeleton.generator.model.metadata.Visibility;

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
    public Visibility visibility;
    public String rendering;
    public List<String> annotations;

}
