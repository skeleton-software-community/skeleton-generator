package org.sklsft.generator.bl.services.interfaces;

import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.repository.dao.datasource.interfaces.DataSourceProvider;

public interface DatabasePopulator {

	void populateDatabase(DataSource dataSource, DataSourceProvider inputSourceProvider, Project project, Set<String> tables);
}
