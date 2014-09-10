package org.sklsft.generator.bl.services.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;

public interface DatabaseUpdater {
	void generateUpdateScript(DatabaseSchema schema, DataSource dataSource, String databaseName, InputDataSourceProvider inputProvider,Project project);
}
