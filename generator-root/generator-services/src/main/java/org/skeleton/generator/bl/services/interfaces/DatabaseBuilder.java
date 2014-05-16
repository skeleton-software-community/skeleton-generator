package org.skeleton.generator.bl.services.interfaces;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.exception.InvalidFileException;
import org.skeleton.generator.model.om.Project;

public interface DatabaseBuilder {
	
	void buildDatabase(DataSource dataSource, Project project) throws InvalidFileException, IOException, SQLException;

}
