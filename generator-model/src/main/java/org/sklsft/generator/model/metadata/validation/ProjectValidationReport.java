package org.sklsft.generator.model.metadata.validation;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class ProjectValidationReport {

	public List<ProjectValidationRecord> records = new ArrayList<>();
	public boolean hasWarnings = false;
	public boolean hasErrors = false;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectValidationReport.class);
	
	public void add (TableMetaData table, ColumnMetaData column, String message) {		
		records.add(new ProjectValidationRecord(Level.WARN, table, column, message));
		hasWarnings = true;
	}
	
	public void addError (TableMetaData table, ColumnMetaData column, String message) {		
		records.add(new ProjectValidationRecord(Level.ERROR, table, column, message));
		hasErrors = true;
	}
	
	
	
	public void print() {
		for (ProjectValidationRecord record : records) {
			StringBuilder messageBuilder = new StringBuilder();
			
			if (record.table != null) {
				messageBuilder.append("Table : " + record.table.getName());
			}
			
			if (record.column != null) {
				messageBuilder.append(", Column : " + record.column.getName());
			}
			
			messageBuilder.append(" -> " + record.message);
			
			if (record.level.equals(Level.ERROR)) {
				logger.error(messageBuilder.toString());
			}
			
			if (record.level.equals(Level.WARN)) {
				logger.warn(messageBuilder.toString());
			}			
		}
	}
	
}
