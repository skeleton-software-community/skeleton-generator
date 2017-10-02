package org.sklsft.generator.bl.services.impl;

import java.util.Set;

import javax.inject.Inject;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.command.impl.BackupCommandFactory;
import org.sklsft.generator.repository.backup.command.interfaces.BackupCommand;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.file.impl.BackupFileLocator;
import org.sklsft.generator.util.folder.FolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * The populator aims at populating the project database following a plan given in the backupPath<br>
 * The plan consists of a root folder withs several steps<br>
 * Each step will execute several {@link BackupCommand}, potentially one per table<br>
 * We can give a list of the tables to be considered if we want to restrict the population to these tables<br>
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class Populator {

	private static final Logger logger = LoggerFactory.getLogger(Populator.class);
	
	@Inject
	private BackupCommandFactory commandFactory;
	
	@Inject
	private BackupFileLocator backupLocator;
	
	
	public void populate(InputDataSourceProvider inputDataSourceProvider, Project project, Set<String> tables, String backupPath) {

		logger.info("start populating database");

		int maxSteps = FolderUtil.resolveMaxStep(backupPath);

		for(int step=1; step<=maxSteps; step++){
			logger.info("start bulding step " + step);
			for (Package myPackage:project.model.packages) {
				logger.info("start populating package : " + myPackage.name);

				for (Table table:myPackage.tables) {

					if (tables == null || tables.contains(table.originalName)) {

						logger.info("start populating table : " + table.name);
						
						PersistenceMode mode = backupLocator.resolvePersistenceModeOrNull(backupPath, step, table);
						
						if (mode != null) {							
							BackupCommand command = commandFactory.getBackupCommand(table, mode, inputDataSourceProvider);								
							String path = backupLocator.getBackupFilePath(backupPath, step, table, mode);										
							command.execute(path);								
							logger.info("populating table : " + table.name + " completed");
							
						} else {
							logger.warn("populating table : " + table.name + " : no backup found");
						}
					} else {
						logger.info("table : " + table.name + " skipped");
					}
				}
				logger.info("populating package " + myPackage.name + " completed");
			}
		}
		logger.info("populating database completed");

	}
}
