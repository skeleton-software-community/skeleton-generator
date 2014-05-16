package org.skeleton.generator.bl.services.impl;

import java.io.File;
import java.util.Set;

import javax.sql.DataSource;

import org.skeleton.generator.bc.factory.reader.BackupArgumentReaderFactory;
import org.skeleton.generator.bc.folder.FolderUtil;
import org.skeleton.generator.bl.services.interfaces.DatabasePopulator;
import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.backup.BackupCommandArguments;
import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupArgumentReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.skeleton.generator.bl.services.impl.TablePopulator;

@Component
public class DatabasePopulatorImpl implements DatabasePopulator {

	private static final Logger logger = LoggerFactory.getLogger(DatabasePopulatorImpl.class);

	private TablePopulator tablePopulator = new TablePopulator();
	private BackupFileLocator backupLocator = new BackupFileLocator();
	private BackupArgumentReaderFactory readerFactory = new BackupArgumentReaderFactory();
	
	@Override
	public void populateDatabase(DataSource dataSource, InputSourceProvider inputSourceProvider, Project project, Set<String> tables) {

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
							BackupArgumentReader argumentReader = readerFactory.getReaderFactory(mode, inputSourceProvider, table);
							BackupCommandArguments commandArgs = argumentReader.readBackupArgs(table, backupLocator.getBackupFilePath(step, table, mode));
							tablePopulator.populateTable(table, dataSource, commandArgs);
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