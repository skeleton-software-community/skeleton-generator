package org.skeleton.generator.bl.services;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bc.folder.FolderUtil;
import org.skeleton.generator.bl.services.impl.BackupFileLocator;
import org.skeleton.generator.exception.BackupFileNotFoundException;
import org.skeleton.generator.model.backup.SourceAndScript;
import org.skeleton.generator.model.check.ScriptCheckWarning;
import org.skeleton.generator.model.check.WarningCheckType;
import org.skeleton.generator.model.metadata.PersistenceMode;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.TableChecker;
import org.skeleton.generator.repository.dao.datasource.impl.XmlFileSourceAndScriptSimpleParser;
import org.skeleton.generator.repository.dao.datasource.interfaces.DataSourceProvider;

/**
 * Checks that the backup scripts are not
 * <ul>
 * <li>reading data from a source that is not the production source</li>
 * <li>referencing an empty table</li>
 * </ul>
 * 
 * Configuration pre-requisite : The {@link #productionSourceRef} needs to be a key to a data source in {@link #inputDataSourceProvider}
 * If these conditions are not met, a warning is presented to the user
 * @author Mounir Regragui
 *
 */
/**

 * @param inputDataSourceProvider map of the consumed databases
 * @param productionSourceRef reference of the production databases
 */
public class BackupScriptChecker {
	
	private DataSourceProvider inputDataSourceProvider;
	private BackupFileLocator backupLocator;
	private XmlFileSourceAndScriptSimpleParser xmlFileSourceAndScriptParser;
	
	private String productionSourceRef;
	private String dbSchema;

	public BackupScriptChecker(BackupFileLocator backupLocator) {
		super();
		this.backupLocator = backupLocator;
		this.xmlFileSourceAndScriptParser = new XmlFileSourceAndScriptSimpleParser();
	}

	private void validateProdRefNotNullAndInInputProvider() {
		if(productionSourceRef==null) throw new IllegalStateException("Production source reference not set in backup script checker");
		DataSource s = inputDataSourceProvider.getDataSource(productionSourceRef);
		if(s==null) throw new IllegalStateException("Production source reference does not reference any input data source");
		
	}

	public List<ScriptCheckWarning> checkScripts(Project project) throws IOException{
		
		validateProdRefNotNullAndInInputProvider();
		
		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
		List<ScriptCheckWarning> result = new LinkedList<>();
		
		for(int step=1; step<=maxSteps; step++){
			List<ScriptCheckWarning> subResult = checkScriptsForStep(project, step);
			result.addAll(subResult);
		}
		
		return result;
	}

	private List<ScriptCheckWarning> checkScriptsForStep(Project project, int step) throws IOException {
		List<ScriptCheckWarning> subResult = new LinkedList<>();
		
		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				
				if(isPersistenceModeCompatible(table, step)){
					List<ScriptCheckWarning> tableCheck = getWarningForTable(table, step);
					subResult.addAll(tableCheck);
				}
					
			}
		}
		
		return subResult;
	}

	private boolean isPersistenceModeCompatible(Table table, int step) {
		try{
			PersistenceMode mode = backupLocator.resolvePersistenceMode(step, table);
			return mode==PersistenceMode.XML;			
		}catch(BackupFileNotFoundException e){
			return false;
		}
	}

	private List<ScriptCheckWarning> getWarningForTable(Table table, int step) throws IOException {
		String filePath = backupLocator.getBackupFilePath(step, table);
		SourceAndScript sourceAndScript = xmlFileSourceAndScriptParser.parse(filePath);
		String sourceRef = sourceAndScript.getSource();
		
		List<ScriptCheckWarning> tableResult = new LinkedList<>();
		
		if(isNotProdSourceTargeted(sourceRef)){
			tableResult.add(new ScriptCheckWarning(WarningCheckType.NOT_PROD_TARGET, step, table));
			
		}
		
		if(isEmptyTableTargeted(sourceAndScript, table)){
			tableResult.add(new ScriptCheckWarning(WarningCheckType.EMPTY_TABLE, step, table));
			
		}
		
		return tableResult;
	}

	private boolean isEmptyTableTargeted(SourceAndScript sourceAndScript, Table table) {
		DataSource inputSource = inputDataSourceProvider.getDataSource(sourceAndScript.getSource());
		TableChecker checker = new TableChecker(inputSource);
		return checker.isTableEmpty(table, dbSchema);
	}

	private boolean isNotProdSourceTargeted(String sourceRef) {
		return !productionSourceRef.equals(sourceRef);
	}

	public void setInputDataSourceProvider(
			DataSourceProvider inputDataSourceProvider) {
		this.inputDataSourceProvider = inputDataSourceProvider;
	}

	public void setProductionSourceRef(String productionSourceRef) {
		this.productionSourceRef = productionSourceRef;
	}
	
	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}



}
