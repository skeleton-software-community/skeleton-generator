package org.sklsft.generator.bc.update;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.reader.BackupArgumentReaderFactory;
import org.sklsft.generator.bc.backup.reader.BackupFileLocator;
import org.sklsft.generator.bc.backup.sql.SqlGenerator;
import org.sklsft.generator.bc.backup.sql.SqlGeneratorFactory;
import org.sklsft.generator.bc.util.folder.FolderUtil;
import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.DatabaseUpdate;
import org.sklsft.generator.model.update.TableUpdate;
import org.sklsft.generator.repository.backup.datasource.impl.BackupCommandArguments;
import org.sklsft.generator.repository.backup.datasource.impl.XmlFileSourceAndScriptSimpleParser;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateScriptGenerator {
	
	@Autowired
	private SqlGeneratorFactory sqlGeneratorFactory;
	
	@Autowired
	private BackupFileLocator backupLocator;
	
	@Autowired
	private BackupArgumentReaderFactory readerFactory;

	
	public List<String> generateUpdateScript(DatabaseUpdate databaseUpdate,
			DataSource dataSource,
			InputDataSourceProvider inputProvider, 
			Project project) {
		SqlGenerator generator = sqlGeneratorFactory.getSqlGenerator(project.databaseEngine);
		List<String>	result =  new ArrayList<String>();
		
		// generation of script to update data model
		//  the scripts are creating in the order of the model
		addUpdateTableScript(result, databaseUpdate, project,generator);

		// creation of procedures by code and FK are created in a second pass
		addProceduresByCode(result, databaseUpdate, project,generator);
		
		// add script to drop table and all associated objects
		addDropOldTables(result, databaseUpdate, project,generator);

		// special commands used to prepare population script
		result.addAll(generator.generateConfigurationPopulation());		

		// preparation of population
		//  remove data from tables completely populate
		addDeleteDataCompletePopulationTables(result, databaseUpdate, project,generator);
		
		// generate population (from file or input source)
		//  the population is done by step and in the model order
		addTablesPopulation(result, databaseUpdate, inputProvider, project, generator);

		return result;
	}
	
	private void addUpdateTableScript(List<String>  result, DatabaseUpdate databaseUpdate, Project project, SqlGenerator generator) {
		for (Package myPackage : project.model.packages) {
			for (Table table : myPackage.tables) {
				if (databaseUpdate.getNewTables().contains(table)) {
					// generate creation script
					result.addAll(generator.generateCreationTableSQL(table));
					result.addAll(generator.generateProceduresTable(table));
				} else  {
					TableUpdate tableUpdate = databaseUpdate.findTableUpdate(table);
					if (tableUpdate != null) {
						// generate update script and recreate associated objects
						result.addAll(generator.generateDropIndexFkTable(table));
						result.addAll(generator.generateDropProceduresTable(table));
						result.addAll(generator.generateUpdateTableSQL(tableUpdate));
						result.addAll(generator.generateProceduresTable(table));						
					}					
				}
			}
		}
	}
	
	private void addProceduresByCode(List<String> result, DatabaseUpdate databaseUpdate, Project project, SqlGenerator generator) {
		for (Package myPackage : project.model.packages) {
			for (Table table : myPackage.tables) {
				if (databaseUpdate.getNewTables().contains(table)
						|| databaseUpdate.findTableUpdate(table) != null) {
					result.addAll(generator.generateAlterFKTableSQL(table));
					result.addAll(generator.generateProceduresByCodeTable(table));
				}
			}
		}
	}

	private void addDropOldTables(List<String> result, DatabaseUpdate databaseUpdate, Project project, SqlGenerator generator) {		
		for (Table table : databaseUpdate.getOldTables()) {
			result.addAll(generator.generateDropProceduresTable(table));
			result.addAll(generator.generateDropTableSQL(table));
		}
	}

	private void addDeleteDataCompletePopulationTables(List<String> result, DatabaseUpdate databaseUpdate, Project project, SqlGenerator generator) {
		if (!databaseUpdate.getCompletePopulateTable().isEmpty()) {
			for (int p = project.model.packages.size() -1; p>=0; --p) {
				Package myPackage = project.model.packages.get(p);			
				for (int t = myPackage.tables.size() - 1; t>=0 ; --t) {
					Table table = myPackage.tables.get(t); 
				
					if (databaseUpdate.getCompletePopulateTable().contains(table)) {
						result.add(generator.generateDeleteSQL(table));
					}
				}
			}
		}
	}
	
	private void addTablesPopulation(List<String> result, DatabaseUpdate databaseUpdate, InputDataSourceProvider inputProvider, Project project, SqlGenerator generator) { 
		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
	
		for(int step=1; step<=maxSteps; step++){
			for (Package myPackage:project.model.packages) {
				for (Table table:myPackage.tables) {
					try {
						PersistenceMode mode = backupLocator.resolvePersistenceMode(step, table);
						boolean useToPopulate = false;
						
						if (mode.equals(PersistenceMode.CSV)) {
							useToPopulate = true;
							//TODO manage the particular case of data to update if exists or create
						} else {
							XmlFileSourceAndScriptSimpleParser parser = new XmlFileSourceAndScriptSimpleParser();
							SourceAndScript sourceAndScript;
							try {
								sourceAndScript = parser.parse(backupLocator.getBackupFilePath(step, table,mode));
							} catch (IOException e) {
								throw new ReadBackupFailureException("Failed to read source and script for table : " + table.name,e);
							}
							if (PopulateCommandType.UPDATE.equals(sourceAndScript.getType())) {
								if (databaseUpdate.getUpdatePopulateTable().contains(table)) {
									useToPopulate = true;
								}
							} else {
								if (databaseUpdate.getCompletePopulateTable().contains(table)) {
									useToPopulate  = true;
								}
							}
						}	
						if (useToPopulate) {
							BackupArgumentReader argumentReader = readerFactory.getBackupArgumentReader(mode, inputProvider, table);
							BackupCommandArguments commandArgs = argumentReader.readBackupArgs(backupLocator.getBackupFilePath(step, table, mode));
							for (Object[] values : commandArgs.getArguments()) {
								if (PopulateCommandType.UPDATE.equals(commandArgs.getType())) {
									result.add(generator.generateUpdateByCode(table, values));
								} else {
									result.add(generator.generateInsertByCode(table, values));
								}
							}
						}
					} catch (BackupFileNotFoundException e) {
					}
				}
			}
		}
	}

}
