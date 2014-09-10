package org.sklsft.generator.model.update;

import java.util.List;

import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Table;

public class TableUpdate {
	private Table				table;
	private List<Column>		columnAdded;
	private List<Column>		columnRemoved;
	
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public List<Column> getColumnAdded() {
		return columnAdded;
	}
	public void setColumnAdded(List<Column> columnAdded) {
		this.columnAdded = columnAdded;
	}
	public List<Column> getColumnRemoved() {
		return columnRemoved;
	}
	public void setColumnRemoved(List<Column> columnRemoved) {
		this.columnRemoved = columnRemoved;
	}
}
