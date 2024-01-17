package org.sklsft.generator.components.validation;

import java.lang.reflect.InvocationTargetException;

import org.sklsft.generator.components.validation.rules.impl.Rules;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;
import org.springframework.stereotype.Component;

@Component
public class ProjectMetaDataValidator {

	public ProjectValidationReport validate(ProjectMetaData project) {

		ProjectValidationReport report = new ProjectValidationReport();

		for (Rules rule : Rules.values()) {
			try {
				report = rule.getCheckerClass().getConstructor().newInstance().checkRules(project, report);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new Error("Could not instantiate : " + rule.getCheckerClass().getName(), e);
			}
		}

		return report;
	}
}
