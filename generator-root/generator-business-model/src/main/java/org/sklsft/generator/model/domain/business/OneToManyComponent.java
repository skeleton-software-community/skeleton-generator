package org.sklsft.generator.model.domain.business;

import org.sklsft.generator.model.domain.database.Column;

public class OneToManyComponent {

	public Bean referenceBean;
    public Column referenceColumn;
    public Bean parentBean;
    public String collectionName;
    public String collectionGetterName;
    public String collectionSetterName;
}
