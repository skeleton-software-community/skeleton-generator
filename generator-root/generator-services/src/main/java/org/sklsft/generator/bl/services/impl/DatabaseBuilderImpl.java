package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sklsft.generator.bl.services.interfaces.DatabaseBuilder;
import org.sklsft.generator.exception.InvalidFileException;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.util.folder.FolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBuilderImpl implements DatabaseBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseBuilderImpl.class);
	
	@Autowired
	DatabaseCleaner databaseCleaner;

	@Override
	public void buildDatabase(DataSource dataSource, Project project) throws InvalidFileException, IOException, SQLException {
		
		logger.info("start cleaning database");
		databaseCleaner.cleanDatabase(dataSource, project);
		
		logger.info("cleaning database completed");
		
		logger.info("start bulding database");
		
		int maxStep = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BUILD_SCRIPT_FOLDER);
		
		for (int step = 1; step <= maxStep; step++) {
			
			logger.info("start bulding step " + step);
		
			for (Package myPackage:project.model.packages) {
				logger.info("start building package : " + myPackage.name);
				
				for (Table table:myPackage.tables) {
					logger.info("start building table : " + table.name);
					
					TableBuilder tableBuilder = new TableBuilder(table, dataSource, step);
					tableBuilder.buildTable();
					
					logger.info("building table : " + table.name + " completed");
				}
				logger.info("building package " + myPackage.name + " completed");
			}
			logger.info("bulding step " + step + " completed");
		}
		
		logger.info("bulding database completed");
		
	}
	


}
