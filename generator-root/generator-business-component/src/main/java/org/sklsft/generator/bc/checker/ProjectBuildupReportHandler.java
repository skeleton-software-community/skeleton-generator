package org.sklsft.generator.bc.checker;

import org.sklsft.generator.model.domain.database.Column;
import org.sklsft.generator.model.domain.database.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Component
public class ProjectBuildupReportHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectBuildupReportHandler.class);

	public void initReport() {
		ProjectBuildupReportHolder.bind(new ProjectBuildupReport());
	}
	
	public void addWarning (Package myPackage, Table table, Column column, String message) {
		
		ProjectBuildupReport report = ProjectBuildupReportHolder.getReport();
		report.records.add(new ProjectBuildupRecord(Level.WARN, myPackage, table, column, message));
		report.hasWarnings = true;
	}
	
	public void addError (Package myPackage, Table table, Column column, String message) {
		
		ProjectBuildupReport report = ProjectBuildupReportHolder.getReport();
		report.records.add(new ProjectBuildupRecord(Level.ERROR, myPackage, table, column, message));
		report.hasErrors = true;
	}
	
	public boolean hasWarnings () {		
		ProjectBuildupReport report = ProjectBuildupReportHolder.getReport();
		return report.hasWarnings;
	}
	
	public boolean hasErrors () {		
		ProjectBuildupReport report = ProjectBuildupReportHolder.getReport();
		return report.hasErrors;
	}
	
	public void printReport() {
		ProjectBuildupReport report = ProjectBuildupReportHolder.getReport();
		for (ProjectBuildupRecord record : report.records) {
			StringBuilder messageBuilder = new StringBuilder();
			if (record.myPackage != null) {
				messageBuilder.append("Package : " + record.myPackage.getName());
			}
			
			if (record.table != null) {
				messageBuilder.append(", Table : " + record.table.originalName);
			}
			
			if (record.column != null) {
				messageBuilder.append(", Column : " + record.column.originalName);
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
	
	public void releaseReport() {
		ProjectBuildupReportHolder.unbind();
	}
}
