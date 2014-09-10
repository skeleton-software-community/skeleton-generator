package org.sklsft.generator.repository.schema.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.model.metadatadb.DatabaseMetaData;

/**
 * Read meta data from a database and provides a DatabaseSchema object 
 * 
 * @author Michael Fernandez
 *
 */
public interface DatabaseMetaDataDao {
	DatabaseMetaData loadDatabaseSchema(DataSource dataSource, String schema);
}
