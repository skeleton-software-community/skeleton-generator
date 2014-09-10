package org.sklsft.generator.bl.services.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;

public interface DatabaseSchemaService {
	DatabaseSchema  loadDatabaseSchema(DataSource dataSource, Project project);
}
