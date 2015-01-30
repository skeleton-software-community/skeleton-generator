package org.sklsft.generator.bc.update;

import java.util.Collection;
import java.util.List;

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
		
		if (isNotEmpty(update.getNewTables())) {
			hasUpdate = true;
			logger.info("Table to create : " + listTable(update.getNewTables()));			
		}
		if (isNotEmpty(update.getModifiedTables())) {
			logger.info("Table to modify : " + listTableUpdate(update.getModifiedTables()));	
			hasUpdate = true;			
		}
		if (isNotEmpty(update.getOldTables())) {
			logger.info("Table to delete : " + listTable(update.getOldTables()));	
			hasUpdate = true;			
		}
		if (isNotEmpty(update.getCompletePopulateTable())) {
			logger.info("Population complete : " + listTable(update.getCompletePopulateTable()));	
			hasUpdate = true;			
		}
		if (isNotEmpty(update.getAddPopulateTable())) {
			logger.info("Population add values : " + listTable(update.getAddPopulateTable()));	
			hasUpdate = true;			
		}
		if (isNotEmpty(update.getUpdatePopulateTable())) {
			logger.info("Population update value : " + listTable(update.getUpdatePopulateTable()));	
			hasUpdate = true;			
		}
				
		
		if (!hasUpdate) {
			logger.info("Nothing to update.");
		}
	}
	private <T> boolean isNotEmpty(Collection<T> table) {
		return table != null && !table.isEmpty();
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
