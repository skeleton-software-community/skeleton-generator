package org.sklsft.generator.model.om;

import java.util.List;

public class DatabaseSchema {
	private String 					databaseUserName;
	private List<Table>				tables;
	
	public String getDatabaseUserName() {
		return databaseUserName;
	}
	public void setDatabaseUserName(String databaseUserName) {
		this.databaseUserName = databaseUserName;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
