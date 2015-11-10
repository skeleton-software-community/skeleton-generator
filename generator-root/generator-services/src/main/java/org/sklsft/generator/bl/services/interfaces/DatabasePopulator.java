package org.sklsft.generator.bl.services.interfaces;

import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;

public interface DatabasePopulator {

	void populateDatabase(DataSource dataSource, InputDataSourceProvider inputSourceProvider, Project project, Set<String> tables);
}
