package org.sklsft.generator.bl.services.interfaces;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.om.Project;

public interface DatabaseBuilder {
	
	void buildDatabase(DataSource dataSource, Project project) throws InvalidFileException, IOException, SQLException;

}
