package org.sklsft.generator.bc.schema.impl;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.bc.schema.interfaces.SchemaFactory;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.metadata.DatabaseEngine;
import org.sklsft.generator.model.metadatadb.ColumnMetaData;
import org.sklsft.generator.model.metadatadb.DataTypeDB;
import org.sklsft.generator.model.metadatadb.DatabaseMetaData;
import org.sklsft.generator.model.metadatadb.TableMetaData;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.springframework.stereotype.Component;

/**
 * Convert the database Schema to generator schema :
 * <li> ignore not business tables
 * <li> convert column types to generator types
 * 
 * @author Michael Fernandez
 */
@Component
public class SchemaFactoryImpl implements SchemaFactory {

	@Override
	public DatabaseSchema createSchema(DatabaseMetaData metaData, Project project) {
		DatabaseSchema schema = new DatabaseSchema();
		List<Table> tables = new ArrayList<Table>();
		for (TableMetaData tableMeta : metaData.getTables()) {
			if (!isAuditTable(tableMeta) && !isGeneratorTable(tableMeta)) {
				Table table = new Table();
				table.name = tableMeta.getTableName();
				table.columns = new ArrayList<Column>();
				
				for (ColumnMetaData columnMeta : tableMeta.getColumns()) {
					Column column = new Column();
					column.dataType = convertDataType(columnMeta.getColumnType(), columnMeta.getColumnSize(), project.getDatabaseEngine());
					column.name = columnMeta.getColumnName();
					table.columns.add(column);
				}
				tables.add(table);
			}
		}
		schema.setTables(tables);
				
		return schema;
	}

	private boolean isAuditTable(TableMetaData table) {
		return table.getTableName().toUpperCase().endsWith("_AUD");
	}
	private boolean isGeneratorTable(TableMetaData table) {
		return table.getTableName().toUpperCase().equals("AUDITENTITY") 
				|| table.getTableName().toUpperCase().equals("DATABASE_VERSION");
	}
	
	private DataType convertDataType(DataTypeDB typeMeta, int size, DatabaseEngine databaseEngine) {
		if (databaseEngine.equals(DatabaseEngine.ORACLE)) {
			return convertOracleDataType(typeMeta,size);
		} else {
			return convertPostgresDataType(typeMeta,size);
		}		
	}
	
	private DataType convertOracleDataType(DataTypeDB typeMeta, int size) {
		DataType result;
		switch (typeMeta) {
		case NUMERIC: result = (size == 1) ? DataType.BOOLEAN: DataType.LONG;break;
		case BOOLEAN : result = DataType.BOOLEAN;break;//boolean are generated in number(1,0)
		case CLOB : result = DataType.TEXT; break;
		case DATE : result = DataType.DATETIME; break;
		case DOUBLE : result = DataType.DOUBLE; break;
		case STRING : result = DataType.STRING; break;		
		default : result = DataType.STRING; 
		}
		return result;
	}
	
	private DataType convertPostgresDataType(DataTypeDB typeMeta, int size) {
		DataType result;
		switch (typeMeta) {
		case NUMERIC: result = (size == 1) ? DataType.BOOLEAN: DataType.LONG;break;
		case BOOLEAN : result = DataType.BOOLEAN;break;
		case CLOB : result = DataType.TEXT; break;
		case DATE : result = DataType.DATETIME; break;
		case DOUBLE : result = DataType.DOUBLE; break;
		case STRING : result = (size > 255) ? DataType.TEXT : DataType.STRING; break;		
		default : result = DataType.STRING; 
		}
		return result;		
	}
}
