package org.sklsft.generator.bc.validation.rules.impl;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public class InvalidReferenceTableChecker implements ProjectMetaDataRuleChecker {

	@Override
	public ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report) {
		
		for (PackageMetaData packageMetaData:project.getPackages()) {
			for (TableMetaData table:packageMetaData.getTables()) {
				if (table.getCardinality() < 0) {
					report.addError(table, null, "Cardinality must be positive");
				}
				if (table.getCardinality() > table.getColumns().size()) {
					report.addError(table, null, "Cardinality must be lower or equal to the number of columns");
				}
			}
		}
		
		return report;		
	}
}
