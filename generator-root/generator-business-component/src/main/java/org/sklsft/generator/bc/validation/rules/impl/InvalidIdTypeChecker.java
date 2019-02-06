package org.sklsft.generator.bc.validation.rules.impl;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;
import org.sklsft.generator.model.metadata.IdGeneratorType;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public class InvalidIdTypeChecker implements ProjectMetaDataRuleChecker {

	@Override
	public ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report) {
		
		for (PackageMetaData packageMetaData:project.getAllPackages()) {
			if (packageMetaData.getTables()!=null) {
				for (TableMetaData table:packageMetaData.getTables()) {
					if (table.getIdType() != null) {
						if (!table.getIdType().isIdElligible()) {
							report.addError(table, null, "Invalid Id Type : " + table.getIdType().name());
						} else {
							if (table.getIdGeneratorType() != null) {
								if (!(table.getIdGeneratorType().equals(IdGeneratorType.NONE) || table.getIdGeneratorType().equals(table.getIdType().getDefaultGenerator()))) {
									report.addError(table, null, "Invalid Id Generator : " + table.getIdGeneratorType().name());
								}
							}
						}
					} else {
						if (table.getIdGeneratorType() != null) {
							if (!(table.getIdGeneratorType().equals(IdGeneratorType.NONE) || table.getIdGeneratorType().equals(IdGeneratorType.SEQUENCE))) {
								report.addError(table, null, "Invalid Id Generator : " + table.getIdGeneratorType().name());
							}
						}
					}
				}
			}
		}
		
		return report;
	}
}
