package org.skeleton.generator.model.om;

import org.skeleton.generator.util.metadata.DataType;
import org.skeleton.generator.util.metadata.Format;
import org.skeleton.generator.util.metadata.RelationType;

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
