package org.sklsft.generator.bc.validation;

import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;
import org.springframework.stereotype.Component;

@Component
public class ProjectMetaDataValidator {

	public ProjectValidationReport validate(ProjectMetaData project) {
		return new ProjectValidationReport();
	}
}
