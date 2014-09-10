package org.sklsft.generator.bc.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sklsft.generator.exception.UpdateFailureException;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Model;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.DatabaseUpdate;
import org.sklsft.generator.model.update.TableUpdate;

/**
 * Analyse the difference between a model and a database schema.
 * Find : 
 * <li>new tables
 * <li>modification of table : add or remove column
 * <br>
 * The following modifications are not allowed (an exception is thrown if found) : 
 * <li>adding column not at the end of a table
 * <li>change a column type
 * <li>change column order
 * 
 * @author Michael Fernandez
 */
public class ModelDifferenceAnalyser {
	private Model				model;
	private DatabaseSchema 		schema;
	private List<Table> 		modelTables;
	private Map<String,Table>	tablesInModel;
	private Map<String,Table>	tablesInSchema;
	
	public ModelDifferenceAnalyser(Model model, DatabaseSchema schema) {
		this.model = model;
		this.schema = schema;
		this.modelTables = extractTables(model);
		this.tablesInModel = createMapTables(modelTables);
		this.tablesInSchema = createMapTables(schema.getTables());
	}
	
	
	public DatabaseUpdate analyseDifference() {
		DatabaseUpdate update = new DatabaseUpdate();
		List<Table>			newTables = new ArrayList<Table>();
		List<TableUpdate>	tableUpdates = new ArrayList<TableUpdate>();
		List<Table>			oldTables = new ArrayList<Table>();
		
		update.setModel(model);
		
		// we search for new tables and table modifications
		for (Table table : modelTables) {
			if (isTableInSchema(table)) {
				Table tableSchema = getTableInSchema(table);
				TableUpdate	tableUpdate = analyseTableDifference(table, tableSchema);
				if (tableUpdate != null) {
					tableUpdates.add(tableUpdate);
				}				
			} else {
				// this is a new table
				newTables.add(table);
			}						
		}
		update.setNewTables(newTables);
		update.setModifiedTables(tableUpdates);
		
		
		// we search for old table (to delete)
		for (Table table : schema.getTables()) {
			if (!isTableInModel(table)) {
				oldTables.add(table);
			}					
		}
		update.setOldTables(oldTables);
		
		
		return update;
	}
	
	/*
	 * 
	 */
	private TableUpdate	 analyseTableDifference(Table tableModel, Table tableSchema) {
		boolean			modification = false;
		TableUpdate		tableUpdate = new TableUpdate();
		List<Column>	columnAdded = new ArrayList<Column>();
		List<Column>	columnRemoved = new ArrayList<Column>();
		int				positionInSchema = 0;
		
		tableUpdate.setTable(tableModel);
		
		for (Column column : tableModel.columns) {
			int pos = findColumnPosition(column.name, tableSchema);
			if (pos < 0) {
				modification = true;
				columnAdded.add(column);
			} else {
				// if a column with the same name exists
				
				//  we check that there is no column added before
				if (!columnAdded.isEmpty()) {
					throw new UpdateFailureException("Column added not at end on table " + tableModel.name + " :" + columnAdded);
				}
				
				// we check that the order is not changed
				if (pos < positionInSchema) {
					throw new UpdateFailureException("Column order changed on table " + tableModel.name + " column " + column.name);
				}
				
				// we check that the type are the same
				Column columnSchema = tableSchema.columns.get(pos); 
				if (columnSchema.dataType != column.dataType) {
					throw new UpdateFailureException("Type modification on table " + tableModel.name + " column " + column.name + "(" + column.dataType + "<>" + columnSchema.dataType + ")");
				}
				
				// the new position in table schema is updated
				positionInSchema = pos;
			}
		}
		tableUpdate.setColumnAdded(columnAdded);
		
		// check for removed column
		for (Column column : tableSchema.columns) {
			if (findColumnPosition(column.name, tableModel) < 0) {
				columnRemoved.add(column);
				modification = true;
			}			
		}
		tableUpdate.setColumnRemoved(columnRemoved);
		
		
		
		if (modification) {
			return tableUpdate;
		} else {
			return null;
		}
	}
	
	private int findColumnPosition(String columnName, Table table) {
		for (int i = 0; i < table.columns.size(); ++i) {
			if (table.columns.get(i).name.equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;
	}
	
	
	private boolean isTableInSchema(Table table) {
		return tablesInSchema.containsKey(table.name.trim().toUpperCase());		
	}

	private boolean isTableInModel(Table table) {
		return tablesInModel.containsKey(table.name.trim().toUpperCase());		
	}
	
	private Table getTableInSchema(Table table) {
		return tablesInSchema.get(table.name.trim().toUpperCase());
	}

	
	private List<Table> extractTables(Model model) {
		List<Table>	tables = new ArrayList<Table>();
		
		for (org.sklsft.generator.model.om.Package pack : model.getPackages()) {
			for (Table table : pack.tables) {
				tables.add(table);
			}			
		}
		return tables;
	}
		
	private Map<String,Table>  createMapTables(List<Table> list) {
		Map<String,Table>	tables = new HashMap<String,Table>();
		for (Table table : list) {
			tables.put(table.name.trim().toUpperCase(),table);
		}
		return tables;
	}
	
}
