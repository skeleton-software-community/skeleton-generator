package org.sklsft.generator.model.domain.database;

import java.util.List;

public class QualifiedColumn {

	public String tableName;
	public String tableAlias;
	public String columnName;
	
	public QualifiedColumn parent;
	public List<QualifiedColumn> children;
	
	public QualifiedColumn(Table table, String tableAlias, Column column) {
		this.tableName = table.name;
		this.tableAlias = tableAlias;
		this.columnName = column.name;
	}
	
	public QualifiedColumn(QualifiedColumn parent, Table table, String tableAlias, Column column) {
		
		this.parent = parent;
		
		this.tableName = table.name;
		this.tableAlias = tableAlias;
		this.columnName = column.name;
	}
}
