package org.skeleton.generator.bl.services.impl;

import java.util.Set;

import javax.sql.DataSource;

import org.skeleton.generator.bl.services.interfaces.DatabasePopulator;
import org.skeleton.generator.bl.services.interfaces.TablePopulator;
import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulatorImpl implements DatabasePopulator {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulatorImpl.class);

	@Override
	public void populateDatabase(DataSource dataSource, InputSourceProvider inputSourceProvider, Project project, Set<String> tables) {
		
		logger.info("start populating database");
		
		for (Package myPackage:project.model.packages) {
			logger.info("start populating package : " + myPackage.name);
			
			for (Table table:myPackage.tables) {
				
				if (tables == null || tables.contains(table.originalName)) {
				
					logger.info("start populating table : " + table.name);
					
					try {
						TablePopulator tablePopulator = TablePopulatorFactory.buildTablePopulator(table, dataSource, inputSourceProvider);
						tablePopulator.populateTable();
						logger.info("populating table : " + table.name + " completed");
					} catch (BackupFileNotFoundException e) {
						logger.error(e.getMessage());
					}
				} else {
					logger.info("table : " + table.name + " skipped");
				}
			}
			logger.info("populating package " + myPackage.name + " completed");
		}
		logger.info("populating database completed");
		
	}
	
}
