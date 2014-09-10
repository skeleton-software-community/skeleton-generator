package org.sklsft.generator.bc.update;

import java.util.Collection;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.DatabaseUpdate;
import org.sklsft.generator.model.update.TableUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateReport {
	private static final Logger logger = LoggerFactory.getLogger(UpdateReport.class);

	public void printUpdateReport(DatabaseUpdate update) {
		boolean		hasUpdate = false;
		
		if (!update.getNewTables().isEmpty()) {
			hasUpdate = true;
			logger.info("Table to create : " + listTable(update.getNewTables()));			
		}
		if (!update.getModifiedTables().isEmpty()) {
			logger.info("Table to modify : " + listTableUpdate(update.getModifiedTables()));	
			hasUpdate = true;			
		}
		if (!update.getOldTables().isEmpty()) {
			logger.info("Table to delete : " + listTable(update.getOldTables()));	
			hasUpdate = true;			
		}
		if (!update.getCompletePopulateTable().isEmpty()) {
			logger.info("Population complete : " + listTable(update.getCompletePopulateTable()));	
			hasUpdate = true;			
		}
		if (!update.getAddPopulateTable().isEmpty()) {
			logger.info("Population add values : " + listTable(update.getAddPopulateTable()));	
			hasUpdate = true;			
		}
		if (!update.getUpdatePopulateTable().isEmpty()) {
			logger.info("Population update value : " + listTable(update.getUpdatePopulateTable()));	
			hasUpdate = true;			
		}
				
		
		if (!hasUpdate) {
			logger.info("Nothing to update.");
		}
		
		
	}
		
	private String listTable(Collection<Table> tables) {
		StringBuffer result = new StringBuffer();
		boolean 	first = true;
		
		for (Table table : tables) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}
		
			result.append(table.name);
		}
		return result.toString();
	}
	private String listTableUpdate(Collection<TableUpdate> tables) {
		StringBuffer result = new StringBuffer();
		boolean 	first = true;
		
		for (TableUpdate table : tables) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}
		
			result.append(table.getTable().name);
		}
		return result.toString();
	}
}
