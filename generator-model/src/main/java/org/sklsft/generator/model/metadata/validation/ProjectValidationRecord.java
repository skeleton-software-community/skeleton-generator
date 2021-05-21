package org.sklsft.generator.model.metadata.validation;

import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.slf4j.event.Level;

public class ProjectValidationRecord {
	
	public ProjectValidationRecord(Level level, TableMetaData table, ColumnMetaData column, String message) {
		super();
		this.level = level;
		this.table = table;
		this.column = column;
		this.message = message;
	}
	
	public Level level;
	public TableMetaData table;
	public ColumnMetaData column;
	public String message;

}
