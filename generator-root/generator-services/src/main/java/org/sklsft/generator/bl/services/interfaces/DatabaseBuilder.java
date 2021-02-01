package org.sklsft.generator.bl.services.interfaces;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.domain.Project;

public interface DatabaseBuilder {
	
	void buildDatabase(BasicDataSource dataSource, Project project) throws InvalidFileException, IOException, SQLException;

}
