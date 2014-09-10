package org.sklsft.generator.model.update;

import java.util.List;
import java.util.Set;

import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Table;

public class DatabaseUpdate {
	private Model				model;
	private List<Table>			newTables;
	private List<Table>			oldTables;
	private List<TableUpdate>	modifiedTables;
	private Set<Table>			completePopulateTable;
	private Set<Table>			updatePopulateTable;
	private Set<Table>			addPopulateTable;
	
	public TableUpdate findTableUpdate(Table table) {
		for (TableUpdate tableUpdate: modifiedTables) {
			if (tableUpdate.getTable().equals(table)) {
				return tableUpdate;
			}
		}
		return null;
	}
	
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public List<Table> getNewTables() {
		return newTables;
	}
	public void setNewTables(List<Table> newTables) {
		this.newTables = newTables;
	}
	public List<Table> getOldTables() {
		return oldTables;
	}
	public void setOldTables(List<Table> oldTables) {
		this.oldTables = oldTables;
	}
	public List<TableUpdate> getModifiedTables() {
		return modifiedTables;
	}
	public void setModifiedTables(List<TableUpdate> modifiedTables) {
		this.modifiedTables = modifiedTables;
	}
	public Set<Table> getCompletePopulateTable() {
		return completePopulateTable;
	}
	public void setCompletePopulateTable(Set<Table> completePopulateTable) {
		this.completePopulateTable = completePopulateTable;
	}
	public Set<Table> getUpdatePopulateTable() {
		return updatePopulateTable;
	}
	public void setUpdatePopulateTable(Set<Table> updatePopulateTable) {
		this.updatePopulateTable = updatePopulateTable;
	}
	public Set<Table> getAddPopulateTable() {
		return addPopulateTable;
	}
	public void setAddPopulateTable(Set<Table> addPopulateTable) {
		this.addPopulateTable = addPopulateTable;
	}
	
}
