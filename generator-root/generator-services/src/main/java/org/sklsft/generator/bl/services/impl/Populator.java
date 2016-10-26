package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.util.Set;

import javax.inject.Inject;

import org.sklsft.generator.exception.BackupFileNotFoundException;
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

@Component
public class Populator {

	private static final Logger logger = LoggerFactory.getLogger(Populator.class);
	
	@Inject
	private BackupCommandFactory commandFactory;
	
	@Inject
	private BackupFileLocator backupLocator;
	
	
	public void populate(InputDataSourceProvider inputDataSourceProvider, Project project, Set<String> tables) {

		logger.info("start populating database");

		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);

		for(int step=1; step<=maxSteps; step++){
			logger.info("start bulding step " + step);
			for (Package myPackage:project.model.packages) {
				logger.info("start populating package : " + myPackage.name);

				for (Table table:myPackage.tables) {

					if (tables == null || tables.contains(table.originalName)) {

						logger.info("start populating table : " + table.name);

						try {
							
							PersistenceMode mode = backupLocator.resolvePersistenceMode(step, table);
							
							BackupCommand command = commandFactory.getBackupCommand(step, table, mode, inputDataSourceProvider);
							
							String path = backupLocator.getBackupFilePath(step, table, mode);
									
							command.execute(path);
							
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
		}
		logger.info("populating database completed");

	}
}
