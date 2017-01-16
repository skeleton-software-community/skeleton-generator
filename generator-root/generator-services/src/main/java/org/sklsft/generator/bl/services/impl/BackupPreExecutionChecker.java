package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.backup.check.BackupPlanPreExecutionWarning;
import org.sklsft.generator.model.backup.check.BackupPlanWarningType;
import org.sklsft.generator.model.domain.Package;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.file.impl.BackupFileLocator;
import org.sklsft.generator.repository.backup.reader.impl.XmlFileSourceAndScriptSimpleParser;
import org.sklsft.generator.util.folder.FolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	@Inject
	private BackupFileLocator backupLocator;
	@Inject
	private XmlFileSourceAndScriptSimpleParser xmlFileSourceAndScriptParser;
	
	
	public List<BackupPlanPreExecutionWarning> checkPlan(InputDataSourceProvider inputDataSourceProvider, Project project, Set<String> tables, String backupPath) throws IOException{
		
		List<BackupPlanPreExecutionWarning> result = new ArrayList<>();
		
		int maxStep = FolderUtil.resolveMaxStep(backupPath);
		
		validateProductionReference(inputDataSourceProvider);
		
		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				if (tables == null || tables.contains(table.originalName)) {
					logger.info("start pre checking table : " + table.name);

					BackupPlanPreExecutionWarning noPlanWarning = checkTableHasPlan(backupPath, maxStep, table);
					
					if (noPlanWarning != null) {
						result.add(noPlanWarning);
					} else {
						result.addAll(checkTableIsPopulatedFromProductionSource(backupPath, maxStep, table, inputDataSourceProvider));
					}
				} else {
					logger.info("table : " + table.name + " skipped");
				}
			}
		}
		
		return result;
		
	}
	
	
	private void validateProductionReference(InputDataSourceProvider inputDataSourceProvider) {
		if(inputDataSourceProvider.getPoductionSourceReference()==null) {
			throw new IllegalStateException("Production source reference not set");
		}
		DataSource dataSource = inputDataSourceProvider.getDataSource(inputDataSourceProvider.getPoductionSourceReference());
		if(dataSource==null) {
			throw new IllegalStateException("Production source reference does not correspond to any input data source");
		}
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
	
	
	private List<BackupPlanPreExecutionWarning> checkTableIsPopulatedFromProductionSource(String backupPath, int maxStep, Table table, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		
		List<BackupPlanPreExecutionWarning> result = new LinkedList<>();
		
		for(int step=1; step<=maxStep; step++){
			result.addAll(checkScriptsForStep(backupPath, step, table, inputDataSourceProvider));
		}
		
		return result;
	}
	

	private List<BackupPlanPreExecutionWarning> checkScriptsForStep(String backupPath, int step, Table table, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		List<BackupPlanPreExecutionWarning> result = new LinkedList<>();

		PersistenceMode mode = backupLocator.resolvePersistenceModeOrNull(backupPath, step, table);
		if(mode!=null){
			switch(mode){
				case XML : 
					result.addAll(checkXmlScript(backupPath, step, table, inputDataSourceProvider));
					break;
				case CSV : 
					result.add(new BackupPlanPreExecutionWarning(BackupPlanWarningType.HARDCODED_VALUES, step, table));
					break;
				default:
					break;
					
			}
		}
		return result;
	}
	

	private List<BackupPlanPreExecutionWarning> checkXmlScript(String backupPath, int step, Table table, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		
		List<BackupPlanPreExecutionWarning> result = new LinkedList<>();
		
		String filePath = backupLocator.getBackupFilePath(backupPath, step, table);
		SourceAndScript sourceAndScript = xmlFileSourceAndScriptParser.parse(filePath);
		String sourceRef = sourceAndScript.getSource();

		if(isNotProdSourceTargeted(sourceRef, inputDataSourceProvider)){
			result.add(new BackupPlanPreExecutionWarning(BackupPlanWarningType.NOT_PROD_TARGET, step, table));
		}

		return result;
	}
	

	private boolean isNotProdSourceTargeted(String sourceRef, InputDataSourceProvider inputDataSourceProvider) {
		return !inputDataSourceProvider.getPoductionSourceReference().equals(sourceRef);
	}

}
