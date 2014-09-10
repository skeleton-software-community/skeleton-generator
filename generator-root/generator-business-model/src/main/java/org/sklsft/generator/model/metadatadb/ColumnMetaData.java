package org.sklsft.generator.model.metadatadb;

public class ColumnMetaData {
	private String		columnName;
	private DataTypeDB columnType; 
	private int		columnSize;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public DataTypeDB getColumnType() {
		return columnType;
	}
	public void setColumnType(DataTypeDB columnType) {
		this.columnType = columnType;
	}
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	
}
