package org.sklsft.generator.model.metadatadb;

import java.util.List;

public class DatabaseMetaData {
	private String					schema;
	private List<TableMetaData>		tables;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public List<TableMetaData> getTables() {
		return tables;
	}
	public void setTables(List<TableMetaData> tables) {
		this.tables = tables;
	}
}
