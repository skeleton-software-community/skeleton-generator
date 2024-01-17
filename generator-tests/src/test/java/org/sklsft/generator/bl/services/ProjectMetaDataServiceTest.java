package org.sklsft.generator.bl.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.sklsft.generator.model.metadata.ProjectMetaData;
import org.sklsft.generator.model.metadata.validation.ProjectValidationReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-generator-test.xml" })
public class ProjectMetaDataServiceTest {
	
	@Autowired
	private ProjectMetaDataService service;
	
	
	@Test
	public void testLoadSuccess() {
		ProjectMetaData project = service.loadProjectMetaData("src/test/resources/projects/success/1");
		ProjectValidationReport report = service.validate(project);
		Assert.assertFalse(report.hasErrors);
		Assert.assertFalse(report.hasWarnings);
	}
	
	
	@Test
	public void testLoadFailureOnDuplicateTables() {
		ProjectMetaData project = service.loadProjectMetaData("src/test/resources/projects/failure/1");
		ProjectValidationReport report = service.validate(project);
		report.print();
		Assert.assertTrue(report.hasErrors);
		Assert.assertFalse(report.hasWarnings);
	}
	
	
	@Test
	public void testLoadFailureOnInvalidTableReference() {
		ProjectMetaData project = service.loadProjectMetaData("src/test/resources/projects/failure/2");
		ProjectValidationReport report = service.validate(project);
		report.print();
		Assert.assertTrue(report.hasErrors);
		Assert.assertFalse(report.hasWarnings);
	}
	
	
	@Test
	public void testLoadFailureOnInvalidIdType() {
		ProjectMetaData project = service.loadProjectMetaData("src/test/resources/projects/failure/3");
		ProjectValidationReport report = service.validate(project);
		report.print();
		Assert.assertTrue(report.hasErrors);
		Assert.assertFalse(report.hasWarnings);
	}
}
