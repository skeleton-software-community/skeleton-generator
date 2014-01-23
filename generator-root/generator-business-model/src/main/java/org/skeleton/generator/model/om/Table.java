package org.skeleton.generator.model.om;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public List<Column> columnList;

	/**
	 * get the list of arguments used in find stored procedure
	 * 
	 * @return
	 */
	public List<Column> getFindColumnList() {
		List<Column> findColumnList = new ArrayList<Column>();
		List<Column> tempColumnList = new ArrayList<Column>();

		for (int i = 1; i <= this.cardinality; i++) {
			if (this.columnList.get(i).referenceTable != null) {
				tempColumnList = this.columnList.get(i).referenceTable.getFindColumnList();
				for (int j = 0; j < tempColumnList.size(); j++) {
					Column column = new Column();
					column.name = columnList.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name;
					column.dataType = tempColumnList.get(j).dataType;
					column.nullable = this.columnList.get(i).nullable;
					findColumnList.add(column);
				}
			} else {
				Column column = new Column();
				column.name = columnList.get(i).name;
				column.dataType = columnList.get(i).dataType;
				column.nullable = this.columnList.get(i).nullable;
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
		List<Column> insertColumnList = new ArrayList<Column>();
		List<Column> tempColumnList = new ArrayList<Column>();

		for (int i = 1; i < this.columnList.size(); i++) {
			if (this.columnList.get(i).referenceTable != null) {
				tempColumnList = this.columnList.get(i).referenceTable.getFindColumnList();
				for (int j = 0; j < tempColumnList.size(); j++) {
					Column column = new Column();
					column.name = columnList.get(i).name.replace("_ID", "_").replace("_id", "_") + tempColumnList.get(j).name;
					column.dataType = tempColumnList.get(j).dataType;
					column.nullable = this.columnList.get(i).nullable;
					insertColumnList.add(column);
				}
			} else {
				Column column = new Column();
				column.name = columnList.get(i).name;
				column.dataType = columnList.get(i).dataType;
				column.nullable = this.columnList.get(i).nullable;
				insertColumnList.add(column);
			}
		}

		return insertColumnList;

	}
	
	public List<QualifiedColumn> getQualifiedColumns() {
		
		Set<String> tableAliases = new HashSet<String>();
		String alias = this.name;
		tableAliases.add(alias);
		
		List<QualifiedColumn> result = new ArrayList<QualifiedColumn>();
		
		for (int i = 1; i< columnList.size(); i++) {
			if (columnList.get(i).referenceTable != null) {
				result.add(getReferenceQualifiedColumn(alias, columnList.get(i), tableAliases));
			} else {
				result.add(new QualifiedColumn(this, alias, columnList.get(i)));
			}
		}
		
		return result;
	}
	
	private QualifiedColumn getReferenceQualifiedColumn(String alias, Column column, Set<String> tableAliases) {
		
		QualifiedColumn qualifiedColumn = new QualifiedColumn(this, alias, column);
		qualifiedColumn.children = column.referenceTable.getReferenceQualifiedChildren(qualifiedColumn, tableAliases);
		
		return qualifiedColumn;
	}
	
	private List<QualifiedColumn> getReferenceQualifiedChildren(QualifiedColumn parent, Set<String> tableAliases) {
		
		List<QualifiedColumn> result = new ArrayList<QualifiedColumn>();
		int j = 1;
		while (tableAliases.contains(this.name + j)) {
			j++;
		}
		String alias = this.name + j;
		tableAliases.add(alias);
		
		for (int i = 1; i<= cardinality; i++) {
			if (columnList.get(i).referenceTable != null) {
				result.add(getReferenceQualifiedColumn(alias, columnList.get(i), tableAliases));
			} else {
				result.add(new QualifiedColumn(parent, this, alias, columnList.get(i)));
			}
		}
		
		return result;
	}
	
	
	
}
