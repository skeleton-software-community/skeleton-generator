package org.sklsft.generator.bc.validation.rules.impl;

import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;
import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public class InvalidReferenceTableChecker implements ProjectMetaDataRuleChecker {

	@Override
	public ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report) {
		
		Map<String, TableMetaData> tablesMap = new HashMap<>();
		
		for (PackageMetaData packageMetaData:project.getAllPackages()) {
			if (packageMetaData.getTables()!=null) {
				for (TableMetaData table:packageMetaData.getTables()) {
					tablesMap.put(table.getName(), table);
				}
			}
		}
		
		for (PackageMetaData packageMetaData:project.getAllPackages()) {
			if (packageMetaData.getTables()!=null) {
				for (TableMetaData table:packageMetaData.getTables()) {
					for (ColumnMetaData column:table.getColumns()) {
						if (column.getReferenceTableName() != null) {
							if (!tablesMap.containsKey(column.getReferenceTableName())) {
								report.addError(table, column, "Invalid table reference");
							}
						}
					}				
				}
			}
		}
		
		return report;
	}
}
