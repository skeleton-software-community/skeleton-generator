package org.sklsft.generator.bc.validation.rules.impl;

import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;
import org.sklsft.generator.model.metadata.ColumnMetaData;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.RelationType;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public class ZeroCardinalityReferenceTableChecker implements ProjectMetaDataRuleChecker {

	@Override
	public ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report) {
		
		Map<String, TableMetaData> tablesMap = new HashMap<>();
		
		for (PackageMetaData packageMetaData:project.getPackages()) {
			if (packageMetaData.getTables()!=null) {
			for (TableMetaData table:packageMetaData.getTables()) {
				tablesMap.put(table.getName(), table);
			}
			}
		}
		
		for (PackageMetaData packageMetaData:project.getPackages()) {
			if (packageMetaData.getTables()!=null) {
				for (TableMetaData table:packageMetaData.getTables()) {
					for (ColumnMetaData column:table.getColumns()) {
						if (column.getReferenceTableName() != null) {
							TableMetaData referenceTable = tablesMap.get(column.getReferenceTableName());
							if (referenceTable != null && referenceTable.getCardinality() == 0 && !RelationType.EMBEDDED.equals(column.getReferenceTableRelation())) {
								report.addError(table, column, "Reference to a zero cardinality table");
							}
						}
					}				
				}
			}
		}
		
		return report;		
	}
}
