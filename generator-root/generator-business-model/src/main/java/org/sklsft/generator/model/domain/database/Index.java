package org.sklsft.generator.model.domain.database;

import java.util.ArrayList;
import java.util.List;

/**
 * representation of a unique constraint<br/>
 * Properties are willingly public because of their intensive use in file write
 * commands<br/>
 * 
 * @author Nicolas Thibault
 * 
 */
public class Index {

	public String name;	
	public List<Column> columns = new ArrayList<>();
	public UniqueConstraint uniqueConstraint = null;
}
