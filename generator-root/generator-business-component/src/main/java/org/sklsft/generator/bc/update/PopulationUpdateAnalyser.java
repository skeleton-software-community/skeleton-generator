package org.sklsft.generator.bc.update;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.sklsft.generator.bc.backup.reader.BackupArgumentReaderFactory;
import org.sklsft.generator.bc.backup.reader.BackupFileLocator;
import org.sklsft.generator.bc.metadata.interfaces.GraphModelFactory;
import org.sklsft.generator.bc.util.folder.FolderUtil;
import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.model.backup.SourceAndScript;
import org.sklsft.generator.model.metadata.PersistenceMode;
import org.sklsft.generator.model.om.GraphModel;
import org.sklsft.generator.model.om.GraphNode;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.DatabaseUpdate;
import org.sklsft.generator.repository.backup.datasource.impl.XmlFileSourceAndScriptSimpleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Analyse table population for a given database update.
 * 
 * @author Michael Fernandez
 */
@Component
public class PopulationUpdateAnalyser {
	
	@Autowired
	private BackupFileLocator backupLocator;
	@Autowired
	private BackupArgumentReaderFactory readerFactory;
	@Autowired
	private GraphModelFactory graphModelFactory;

	public void analysePopulation(Project project, DatabaseUpdate databaseUpdate) {
		int maxSteps = FolderUtil.resolveMaxStep(project.sourceFolder + File.separator + Project.BACKUP_SCRIPT_FOLDER);
		databaseUpdate.setCompletePopulateTable(new HashSet<Table>());
		databaseUpdate.setUpdatePopulateTable(new HashSet<Table>());
		databaseUpdate.setAddPopulateTable(new HashSet<Table>());
		
		for (Package myPackage : project.model.packages) {
			for (Table table : myPackage.tables) {
				if (databaseUpdate.getNewTables().contains(table)) {
					// the new tables are completely populated
					databaseUpdate.getCompletePopulateTable().add(table);
				} else {					
					analysePopulationTable(table, databaseUpdate, maxSteps);										
				}
			}
		}
		
		// create a graph of table depencies to be able to determine 
		// the dependant tables to populate completely.
		if (!databaseUpdate.getCompletePopulateTable().isEmpty()) {
			GraphModel graph = graphModelFactory.buildGraphModel(project.model);
			analyseDependantPopulationTable(databaseUpdate, graph);
		}
			
	}
						
	private void analysePopulationTable(Table table, DatabaseUpdate databaseUpdate, int maxSteps) {
		boolean hasHarcodedValues = false;
		boolean hasInsert = false;
		boolean hasUpdate = false;
		
		for (int step = 1; step <= maxSteps; step++) {
			try {
				PersistenceMode mode = backupLocator.resolvePersistenceMode(step, table);
				
				if (mode.equals(PersistenceMode.CSV)) {
					hasHarcodedValues = true;
				} else {
					XmlFileSourceAndScriptSimpleParser parser = new XmlFileSourceAndScriptSimpleParser();
					SourceAndScript sourceAndScript;
					try {
						sourceAndScript = parser.parse(backupLocator.getBackupFilePath(step, table,mode));
					} catch (IOException e) {
						throw new ReadBackupFailureException("Failed to read source and script for table : " + table.name,e);
					}
					if (PopulateCommandType.UPDATE.equals(sourceAndScript.getType())) {
						hasUpdate = true;
					} else {
						hasInsert = true;
					}
				}							
			} catch (BackupFileNotFoundException notfound) {
				//do nothing
			}
		}
		
		if (hasHarcodedValues) {
			if (hasInsert) {
				// we will add only hasHarcorded values
				databaseUpdate.getAddPopulateTable().add(table);				
			} else {
				// table is completely populated
				databaseUpdate.getCompletePopulateTable().add(table);
			}
		} 
		if (hasUpdate) {
			if (!hasInsert) {
				// this table will be updated
				databaseUpdate.getUpdatePopulateTable().add(table);
			} else if (databaseUpdate.findTableUpdate(table) != null) {
				// in case of table structure change, we will execute all the update data
				databaseUpdate.getUpdatePopulateTable().add(table);				
			}
		}		
	}
	private void analyseDependantPopulationTable(DatabaseUpdate databaseUpdate, GraphModel graph) {
			Set<Table > allTablesToPopulate = new HashSet<Table>();
			
			for (Table table : databaseUpdate.getCompletePopulateTable()) {
				addDependantTable(table, allTablesToPopulate, graph);
		}
		
		databaseUpdate.getCompletePopulateTable().addAll(allTablesToPopulate);
		
	}
	
	private void addDependantTable(Table table, Set<Table> tables, GraphModel graph) {
		if (!tables.contains(table)) {
			
			tables.add(table);
			
			GraphNode node = graph.getNode(table);
			
			for (GraphNode dependant : node.getDependantTables()) {
				addDependantTable(dependant.getTable(), tables, graph);
			}
		}
	}
		
}
