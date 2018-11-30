package org.sklsft.generator.bc.validation.rules.impl;

import java.util.HashMap;
import java.util.Map;

import org.sklsft.generator.bc.validation.rules.ProjectMetaDataRuleChecker;
import org.sklsft.generator.model.metadata.PackageMetaData;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.TableMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public class NoDuplicateTableChecker implements ProjectMetaDataRuleChecker {

	@Override
	public ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report) {

		Map<String, TableMetaData> tablesMap = new HashMap<>();

		for (PackageMetaData packageMetaData : project.getAllPackages()) {
			if (packageMetaData.getTables() != null) {
				for (TableMetaData table : packageMetaData.getTables()) {
					if (tablesMap.containsKey(table.getName().toLowerCase())) {
						report.addError(table, null, "Duplicate table");
					}
					tablesMap.put(table.getName().toLowerCase(), table);
				}
			}
		}

		return report;
	}

}
