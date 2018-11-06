package org.sklsft.generator.bc.checker;

import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.slf4j.event.Level;

public class ProjectBuildupRecord {
	
	public ProjectBuildupRecord(Level level, Package myPackage, Table table, Column column, String message) {
		super();
		this.level = level;
		this.myPackage = myPackage;
		this.table = table;
		this.column = column;
		this.message = message;
	}
	
	public Level level;
	public Package myPackage;
	public Table table;
	public Column column;
	public String message;

}
