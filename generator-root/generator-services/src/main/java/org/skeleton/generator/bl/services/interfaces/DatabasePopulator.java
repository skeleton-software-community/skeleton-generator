package org.skeleton.generator.bl.services.interfaces;

import java.util.Set;

import javax.sql.DataSource;

import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;

public interface DatabasePopulator {

	void populateDatabase(DataSource dataSource, InputSourceProvider inputSourceProvider, Project project, Set<String> tables);
}
