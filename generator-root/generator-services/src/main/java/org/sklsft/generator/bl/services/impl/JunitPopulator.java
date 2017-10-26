package org.sklsft.generator.bl.services.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import javax.inject.Inject;

import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.command.impl.BackupCommandFactory;
import org.sklsft.generator.repository.backup.command.interfaces.BackupCommand;
import org.sklsft.generator.repository.backup.file.impl.BackupFileLocator;
import org.sklsft.generator.util.folder.FolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The JunitPopulator is very similar to the @{link Populator}
 * The only differences are :
 * <li>We can restrict by packages and not tables : giving a list of tables would too tedious
 * <li>We don't consider any datasource to connect to except the project one. The only {@link PersistenceMode} to consider is CSV
 * 
 * @author Nicolas Thibault
 *
 */
@Component
public class JunitPopulator {

	private static final Logger logger = LoggerFactory.getLogger(JunitPopulator.class);
	
	@Inject
	private BackupCommandFactory commandFactory;
	
	@Inject
	private BackupFileLocator backupLocator;
	
	
	public void populate(Project project, Set<String> packages, String backupPath) {

		logger.info("start populating database");

		int maxSteps = FolderUtil.resolveMaxStep(backupPath);

		for (int step = 1; step <= maxSteps; step++) {
			logger.info("start bulding step " + step);
			for (Package myPackage : project.model.packages) {

				if (packages == null || packages.isEmpty() || packages.contains(myPackage.declaredName)) {

					logger.info("start populating package : " + myPackage.name);

					for (Bean bean : myPackage.beans) {
						
						if (!bean.isEmbedded) {

							logger.info("start populating table : " + bean.table.name);					
	
							BackupCommand command = commandFactory.getBackupCommand(bean.table, PersistenceMode.CSV, null);
	
							String path = backupLocator.getBackupFilePath(backupPath, step, bean.table, PersistenceMode.CSV);
							
							if (Files.exists(Paths.get(path))) {
								command.execute(path);
								logger.info("populating table : " + bean.table.name + " completed");
							} else {
								logger.warn("populating table : " + bean.table.name + " : no backup found");
							}
						}
					}
					logger.info("populating package " + myPackage.name + " completed");
				} else {
					logger.info("package : " + myPackage.name + " skipped");
				}				
			}
		}
		logger.info("populating database completed");
	}
}
