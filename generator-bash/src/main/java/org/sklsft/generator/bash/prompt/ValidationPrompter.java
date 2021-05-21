package org.sklsft.generator.bash.prompt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sklsft.generator.model.exception.ProjectInitFailureException;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationPrompter {
	
	private static final Logger logger = LoggerFactory.getLogger(ValidationPrompter.class);

	public static void promptOnValidation(ProjectValidationReport report) throws IOException {
	
		report.print();
		if (report.hasErrors) {
			throw new ProjectInitFailureException("validation.failed");
		}
		if (report.hasWarnings) {
			System.out.println("Warnings were found during validation. Do you wish to continue? [Y/n]");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String input = bufferRead.readLine();
			
			if(!("Y".equals(input)||input.isEmpty())){
				logger.warn("Aborting ...");
				System.exit(0);
			}
		}
	}
}
