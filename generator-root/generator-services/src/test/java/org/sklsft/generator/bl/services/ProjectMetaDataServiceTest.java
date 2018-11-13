package org.sklsft.generator.bl.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sklsft.generator.bl.services.interfaces.ProjectMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-generator-test.xml" })
public class ProjectMetaDataServiceTest {

	/*
	 * logger
	 */
	private final Logger logger = LoggerFactory.getLogger(ProjectMetaDataServiceTest.class);
	
	@Inject
	private ProjectMetaDataService service;
	
	
	@Test
	public void testSetup() {
		logger.info("Your test environment is ready to use !");			
	}
	
	@Test
	public void testLoadSuccess() {
		service.loadProjectMetaData("src/test/resources/projects/success/1");
	}
}
