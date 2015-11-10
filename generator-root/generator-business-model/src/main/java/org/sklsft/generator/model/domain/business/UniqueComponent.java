package org.sklsft.generator.model.domain.business;

import org.sklsft.generator.model.domain.database.Column;

public class UniqueComponent {

	public Bean referenceBean;
    public Column referenceColumn;
    public Bean parentBean;
    public String getterName;
    public String setterName;
}
