package org.sklsft.generator.bl.services.interfaces;

import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Project;

/**
 * Dump business content of database into text files
 * @author Michael Fernandez
 */
public interface DatabaseDumper {

	void dumpDatabase(DataSource dataSource, Project project, Set<String> tables);
}
