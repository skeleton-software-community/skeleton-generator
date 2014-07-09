package org.skeleton.generator.bc.check;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bc.folder.FolderUtil;
import org.skeleton.generator.model.check.ScriptCheckWarning;
import org.skeleton.generator.model.check.WarningCheckType;
import org.skeleton.generator.model.om.Package;
import org.skeleton.generator.model.om.Project;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.DataSourceProvider;

/**
 * Checks that the backup scripts are not
 * <ul>
 * <li>reading data from a source that is not the production source</li>
 * <li>referencing an empty table</li>
 * </ul>
 * If these conditions are not met, a warning is presented to the user
 * @author Mounir Regragui
 *
 */
public class BackupScriptChecker {
	
	
	private DataSourceProvider inputDataSourceProvider;
	private String productionSourceRef;
	
	/**
	 * Pre-requisite : The {@link #productionSourceRef} needs to be a key to a data source in {@link #inputDataSourceProvider}
	 * @param inputDataSourceProvider map of the consumed databases
	 * @param productionSourceRef reference of the production databases
	 */
	public BackupScriptChecker(DataSourceProvider inputDataSourceProvider, String productionSourceRef) {
		super();
		this.inputDataSourceProvider = inputDataSourceProvider;
		this.productionSourceRef = productionSourceRef;
		validateProdRefInInputProvider();
	}

	private void validateProdRefInInputProvider() {
		// TODO Auto-generated method stub
		
	}

	public List<ScriptCheckWarning> checkScripts(DataSource outputDataSource, Project project){
		
		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
		List<ScriptCheckWarning> result = new LinkedList<>();
		
		for(int step=1; step<=maxSteps; step++){
			List<ScriptCheckWarning> subResult = checkScriptsForStep(outputDataSource, project, step);
			result.addAll(subResult);
		}
		
		return result;
	}

	private List<ScriptCheckWarning> checkScriptsForStep(DataSource outputDataSource, Project project, int step) {
		List<ScriptCheckWarning> subResult = new LinkedList<>();
		
		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				
				ScriptCheckWarning tableCheck = getWarningForTableOrNull(outputDataSource, table, step);
				if(tableCheck!=null) subResult.add(tableCheck);
					
			}
		}
		
		return subResult;
	}

	private ScriptCheckWarning getWarningForTableOrNull(DataSource outputDataSource, Table table, int step) {
		if(isNotProdSourceTargeted()){
			return new ScriptCheckWarning(WarningCheckType.NOT_PROD_TARGET, step, table);
			
		}else if(isEmptyTableTargeted()){
			return new ScriptCheckWarning(WarningCheckType.EMPTY_TABLE, step, table);
			
		}else return null;
	}

	private boolean isEmptyTableTargeted() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isNotProdSourceTargeted() {
		// TODO Auto-generated method stub
		return false;
	}



}
