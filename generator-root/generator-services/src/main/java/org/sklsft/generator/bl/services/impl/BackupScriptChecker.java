package org.sklsft.generator.bl.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.reader.BackupFileLocator;
import org.sklsft.generator.bc.util.folder.FolderUtil;
import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.check.ScriptCheckWarning;
import org.sklsft.generator.model.check.WarningCheckType;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.datasource.impl.XmlFileSourceAndScriptSimpleParser;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.jdbc.impl.TableChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class BackupScriptChecker {

	@Autowired
	private BackupFileLocator backupLocator;
	@Autowired
	private XmlFileSourceAndScriptSimpleParser xmlFileSourceAndScriptParser;
	

	private void validateProdRefNotNullAndInInputProvider(InputDataSourceProvider inputDataSourceProvider) {
		if(inputDataSourceProvider.getPoductionSourceReference()==null) throw new IllegalStateException("Production source reference not set in backup script checker");
		DataSource s = inputDataSourceProvider.getDataSource(inputDataSourceProvider.getPoductionSourceReference());
		if(s==null) throw new IllegalStateException("Production source reference does not reference any input data source");

	}

	public List<ScriptCheckWarning> checkScripts(Project project, InputDataSourceProvider inputDataSourceProvider) throws IOException{
		List<ScriptCheckWarning> subResult1 = checkFromProject(project);
		List<ScriptCheckWarning> subResult2 = checkInDatabase(project, inputDataSourceProvider);
		List<ScriptCheckWarning> result = new ArrayList<>(subResult1.size() + subResult2.size());
		result.addAll(subResult1);
		result.addAll(subResult2);
		return result;
	}

	private List<ScriptCheckWarning> checkFromProject(Project project){
		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
		
		List<ScriptCheckWarning> result = new LinkedList<>();
		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				if(!hasTableAScript(table, maxSteps)){
					result.add(new ScriptCheckWarning(WarningCheckType.NO_PLAN, ScriptCheckWarning.NO_STEP, table));
				}
			}
		}
		return result;
	}

	private boolean hasTableAScript(Table table, int maxSteps) {
		return backupLocator.existsFileForTable(table, maxSteps);
	}

	private List<ScriptCheckWarning> checkInDatabase(Project project, InputDataSourceProvider inputDataSourceProvider)
			throws IOException {
		validateProdRefNotNullAndInInputProvider(inputDataSourceProvider);

		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
		List<ScriptCheckWarning> result = new LinkedList<>();

		for(int step=1; step<=maxSteps; step++){
			List<ScriptCheckWarning> subResult = checkScriptsForStep(project, step, inputDataSourceProvider);
			result.addAll(subResult);
		}

		return result;
	}

	private List<ScriptCheckWarning> checkScriptsForStep(Project project, int step, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		List<ScriptCheckWarning> subResult = new LinkedList<>();

		for (Package myPackage:project.model.packages) {
			for (Table table:myPackage.tables) {
				PersistenceMode mode = backupLocator.resolvePersistenceModeOrNull(step, table);
				if(mode!=null){
					switch(mode){
					case XML : subResult.addAll(checkXmlScript(table, step, inputDataSourceProvider)); break;
					case CSV : subResult.add(checkCsvScript(table, step)); break;
					}
				}
			}
		}

		return subResult;
	}

	private ScriptCheckWarning checkCsvScript(Table table, int step){
		return new ScriptCheckWarning(WarningCheckType.HARDCODED_VALUES, step, table);
	}

	private List<ScriptCheckWarning> checkXmlScript(Table table, int step, InputDataSourceProvider inputDataSourceProvider) throws IOException {
		String filePath = backupLocator.getBackupFilePath(step, table);
		SourceAndScript sourceAndScript = xmlFileSourceAndScriptParser.parse(filePath);
		String sourceRef = sourceAndScript.getSource();

		List<ScriptCheckWarning> tableResult = new LinkedList<>();

		if(isNotProdSourceTargeted(sourceRef, inputDataSourceProvider)){
			tableResult.add(new ScriptCheckWarning(WarningCheckType.NOT_PROD_TARGET, step, table));

		}

		if(isEmptyTableTargeted(sourceAndScript, table, inputDataSourceProvider)){
			tableResult.add(new ScriptCheckWarning(WarningCheckType.EMPTY_TABLE, step, table));

		}

		return tableResult;
	}

	private boolean isEmptyTableTargeted(SourceAndScript sourceAndScript, Table table, InputDataSourceProvider inputDataSourceProvider) {
		DataSource inputSource = inputDataSourceProvider.getDataSource(sourceAndScript.getSource());
		TableChecker checker = new TableChecker(inputSource);
		return checker.isTableEmpty(table);
	}

	private boolean isNotProdSourceTargeted(String sourceRef, InputDataSourceProvider inputDataSourceProvider) {
		return !inputDataSourceProvider.getPoductionSourceReference().equals(sourceRef);
	}

}
