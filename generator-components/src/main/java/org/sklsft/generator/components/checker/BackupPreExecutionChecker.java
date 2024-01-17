package org.sklsft.generator.components.checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.sklsft.generator.model.backup.check.BackupPlanPreExecutionWarning;
import org.sklsft.generator.model.backup.check.BackupPlanWarningType;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.util.folder.FolderUtil;
import org.sklsft.generator.persistence.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.persistence.backup.file.impl.BackupFileLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Checks if the backup scripts are
 * <ul>
 * <li>reading data from a source that is not the production source</li>
 * <li>reading data from hardcoded values</li>
 * <li>absent !
 * </ul>
 * 
 * Configuration pre-requisite : The productionSourceReference needs to be defined in the {@link InputDataSourceProvider}
 * If these conditions are not met, a warning is presented to the user
 * @author Mounir Regragui
 *
 */
@Component
public class BackupPreExecutionChecker {
	
	private static final Logger logger = LoggerFactory.getLogger(BackupPreExecutionChecker.class);
	
	@Autowired
	private BackupFileLocator backupLocator;
	
	
	public List<BackupPlanPreExecutionWarning> checkPlan(InputDataSourceProvider inputDataSourceProvider, Project project, Set<String> tables, String backupPath) throws IOException{
		
		List<BackupPlanPreExecutionWarning> result = new ArrayList<>();
		
		int maxStep = FolderUtil.resolveMaxStep(backupPath);
		
		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				if (tables == null || tables.contains(table.originalName)) {
					logger.info("start pre checking table : " + table.name);

					BackupPlanPreExecutionWarning noPlanWarning = checkTableHasPlan(backupPath, maxStep, table);
					
					if (noPlanWarning != null) {
						result.add(noPlanWarning);
					} else {
						result.addAll(checkHardCodedBackup(backupPath, maxStep, table, inputDataSourceProvider));
					}
				} else {
					logger.info("table : " + table.name + " skipped");
				}
			}
		}
		
		return result;
		
	}


	private BackupPlanPreExecutionWarning checkTableHasPlan(String backupPath, int maxStep, Table table){
				
		if(!tableHasPlan(backupPath, maxStep, table)){
			return new BackupPlanPreExecutionWarning(BackupPlanWarningType.NO_PLAN, BackupPlanPreExecutionWarning.NO_STEP, table);
		}
		return null;
	}
	
	
	private boolean tableHasPlan(String backupPath, int maxSteps, Table table) {
		return backupLocator.existsFileForTable(backupPath, maxSteps, table);
	}
	
	
	private List<BackupPlanPreExecutionWarning> checkHardCodedBackup(String backupPath, int maxStep, Table table, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		
		List<BackupPlanPreExecutionWarning> result = new LinkedList<>();
		
		for(int step=1; step<=maxStep; step++){
			result.addAll(checkHardCodedBackupForStep(backupPath, step, table, inputDataSourceProvider));
		}
		
		return result;
	}
	

	private List<BackupPlanPreExecutionWarning> checkHardCodedBackupForStep(String backupPath, int step, Table table, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		List<BackupPlanPreExecutionWarning> result = new LinkedList<>();

		PersistenceMode mode = backupLocator.resolvePersistenceModeOrNull(backupPath, step, table);
		if(mode!=null){
			switch(mode){
				case TXT : 
					result.add(new BackupPlanPreExecutionWarning(BackupPlanWarningType.HARDCODED_VALUES, step, table));
					break;
				default:
					break;
					
			}
		}
		return result;
	}
}
