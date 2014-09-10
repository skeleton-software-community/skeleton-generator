package org.sklsft.generator.bl.services.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.bc.schema.interfaces.SchemaFactory;
import org.sklsft.generator.bl.services.interfaces.DatabaseSchemaService;
import org.sklsft.generator.model.metadatadb.DatabaseMetaData;
import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.schema.interfaces.DatabaseMetaDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Load a database schema (PUBLIC tables and user connected tables) with JDBC.
 * Convert the schema to a compatible skeleton schema.
 * 
 * @author Michael Fernandez
 */
@Component
public class DatabaseSchemaServiceImpl implements DatabaseSchemaService {
	/*
	 * properties injected by spring
	 */
	@Autowired
	DatabaseMetaDataDao databaseMetaDataDao;
	
	@Autowired
	SchemaFactory schemaFactory;
	

	@Override
	public DatabaseSchema loadDatabaseSchema(DataSource dataSource, Project project) {
		String userName = project.getDatabaseUserName();
		if (dataSource instanceof BasicDataSource)  {
			userName = ((BasicDataSource)dataSource).getUsername();
		}
		DatabaseMetaData databaseMetaData = databaseMetaDataDao.loadDatabaseSchema(dataSource, userName);
		DatabaseSchema schema = schemaFactory.createSchema(databaseMetaData, project);
				
		return schema;
	}
	
}
