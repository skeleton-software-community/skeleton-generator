package org.sklsft.generator.repository.schema.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.exception.UpdateFailureException;
import org.sklsft.generator.model.metadatadb.ColumnMetaData;
import org.sklsft.generator.model.metadatadb.DataTypeDB;
import org.sklsft.generator.model.metadatadb.DatabaseMetaData;
import org.sklsft.generator.model.metadatadb.TableMetaData;
import org.sklsft.generator.repository.schema.interfaces.DatabaseMetaDataDao;
import org.springframework.stereotype.Component;

/**
 * Implementation that uses jdbc meta data 
 * @author Michael Fernandez
 *
 */
@Component(value="databaseMetaDataDao")
public class DatabaseMetaDataDaoImpl implements DatabaseMetaDataDao {
	private static final String SCHEMA_PUBLIC = "PUBLIC";
	
	@Override
	public DatabaseMetaData loadDatabaseSchema(DataSource dataSource,
			String schema) {
		try {
			DatabaseMetaData 			result = new DatabaseMetaData();
			Connection 					conn = dataSource.getConnection();
			java.sql.DatabaseMetaData 	metaDataSQL = conn.getMetaData();
			ResultSet 					rs;
			
			rs = metaDataSQL.getTables(null, null, "%", new String[] {"TABLE"});
			List<TableMetaData> tables = new ArrayList<TableMetaData>();			
			while(rs.next()) {
				String schemaTable = rs.getString(2);
				if (SCHEMA_PUBLIC.equalsIgnoreCase(schemaTable) || schema.equalsIgnoreCase(schemaTable)) {
					TableMetaData table = new TableMetaData();
					table.setTableName(rs.getString(3));
					tables.add(table);
				}
			}
			rs.close();
			result.setTables(tables);
			
			if (tables.isEmpty()) {
				throw new UpdateFailureException("Error schema is empty for schema " + schema);
			}
			
			
			for (TableMetaData table : tables) {
				rs = metaDataSQL.getColumns(null, null, table.getTableName(), "%");
				List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();
				
				while(rs.next()) {
					ColumnMetaData column = new ColumnMetaData();
					column.setColumnName(rs.getString(4));
					column.setColumnType(convertSqlType(rs.getInt(5)));
					column.setColumnSize(rs.getInt(7));
					columns.add(column);					
				}
				table.setColumns(columns);				
			}
						
			return result;			
		} catch (SQLException sqle) {
			throw new UpdateFailureException("Sql error to retrieve meta data", sqle);
		}
	}

	private DataTypeDB convertSqlType(int sqlType) {
		DataTypeDB result;
		switch (sqlType) {
		case Types.BIGINT:
		case Types.INTEGER:
		case Types.NUMERIC:
		case Types.BIT:
		case Types.DECIMAL:
		case Types.SMALLINT: result = DataTypeDB.NUMERIC;break;
		case Types.DOUBLE:
		case Types.FLOAT:result = DataTypeDB.DOUBLE;break;
		case Types.BLOB:
		case Types.CLOB:
		case Types.NCLOB: result = DataTypeDB.CLOB;break;
		case Types.VARCHAR:
		case Types.CHAR: result = DataTypeDB.STRING;break;
		case Types.DATE:
		case Types.TIMESTAMP: result = DataTypeDB.DATE; break;
		default:
			throw new UpdateFailureException("Unknown sql data type : " + sqlType);
		}
		return result;		
	}
}
