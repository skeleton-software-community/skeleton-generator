package org.sklsft.generator.components.validation.rules;

import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;

public interface ProjectMetaDataRuleChecker {

	ProjectValidationReport checkRules(ProjectMetaData project, ProjectValidationReport report);
}
