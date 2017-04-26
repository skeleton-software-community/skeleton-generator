package org.sklsft.generator.model.domain.database;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.domain.Package;

/**
 * representation of a table<br/>
 * Properties are willingly public because of their intensive use in file write
 * commands<br/>
 * The cardinality of a table represents the number of fields that are needed to
 * build the business key (and its associated unique constraint)
 * 
 * @author Nicolas Thibault
 * 
 */
public class Table {

	public Package myPackage;
	public String name;
	public String originalName;
	public int cardinality;

	public List<Column> columns = new ArrayList<>();

	/**
	 * get the list of arguments used in find stored procedure
	 * 
	 * @return
	 */
	public List<Column> getFindColumnList() {
		List<Column> findColumnList = new ArrayList<Column>();
		List<Column> tempColumnList = new ArrayList<Column>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.columns.get(i).referenceTable != null) {
				tempColumnList = this.columns.get(i).referenceTable.getFindColumnList();
				for (int j = 0; j < tempColumnList.size(); j++) {
					Column column = new Column();
					column.name = columns.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name;
					column.dataType = tempColumnList.get(j).dataType;
					column.nullable = this.columns.get(i).nullable;
					findColumnList.add(column);
				}
			} else {
				Column column = new Column();
				column.name = columns.get(i).name;
				column.dataType = columns.get(i).dataType;
				column.nullable = this.columns.get(i).nullable;
				findColumnList.add(column);
			}
		}

		return findColumnList;

	}

	/**
	 * get the list of arguments used in insert by code stored procedure
	 * 
	 * @return
	 */
	public List<Column> getInsertColumnList() {
		List<Column> result = new ArrayList<>();
		List<Column> tempColumnList = new ArrayList<>();

		for (Column currentColumn:this.columns) {
			if (currentColumn.referenceTable != null) {
				tempColumnList = currentColumn.referenceTable.getFindColumnList();
				for (Column tempColumn : tempColumnList) {
					Column column = new Column();
					column.name = currentColumn.name.replace("_ID", "_").replace("_id", "_") + tempColumn.name;
					column.dataType = tempColumn.dataType;
					column.nullable = currentColumn.nullable;
					result.add(column);
				}
			} else {
				Column column = new Column();
				column.name = currentColumn.name;
				column.dataType = currentColumn.dataType;
				column.nullable = currentColumn.nullable;
				result.add(column);
			}
		}

		return result;

	}
}
