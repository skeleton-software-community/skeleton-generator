package org.sklsft.generator.model.domain.database;

import java.util.List;

import org.sklsft.generator.model.domain.business.Property;

/**
 * representation of a unique constraint<br/>
 * Properties are willingly public because of their intensive use in file write
 * commands<br/>
 * 
 * @author Nicolas Thibault
 * 
 */
public class UniqueConstraint {

	public String name;	
	public List<Column> columns;	
	public List<Property> properties;
}
