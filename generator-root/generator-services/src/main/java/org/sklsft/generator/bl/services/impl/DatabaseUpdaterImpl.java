package org.sklsft.generator.bl.services.impl;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.file.command.impl.sql.update.UpdateScriptFileWriterCommand;
import org.sklsft.generator.bc.update.ModelDifferenceAnalyzer;
import org.sklsft.generator.bc.update.PopulationUpdateAnalyzer;
import org.sklsft.generator.bc.update.UpdateReport;
import org.sklsft.generator.bc.update.UpdateScriptGenerator;
import org.sklsft.generator.bl.services.interfaces.DatabaseUpdater;
import org.sklsft.generator.exception.UpdateFailureException;
import org.sklsft.generator.model.om.DatabaseSchema;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.update.DatabaseUpdate;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUpdaterImpl implements DatabaseUpdater {

	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(DatabaseUpdaterImpl.class);

	@Autowired
	private PopulationUpdateAnalyzer populationAnalyzer;
	@Autowired 
	private UpdateReport updateReport;
	@Autowired
	private UpdateScriptGenerator generator;
	
	@Override
	public void generateUpdateScript(DatabaseSchema schema,			
			DataSource dataSource, String databaseName, 
			InputDataSourceProvider inputProvider, Project project) {
		ModelDifferenceAnalyzer differenceAnalyzer = new ModelDifferenceAnalyzer(project.model, schema);		
		
		// find difference in the schema
		logger.info("start analysing difference between schema and skeleton");				
		DatabaseUpdate update = differenceAnalyzer.analyzeDifference();
		logger.info("end analysing difference between schema and skeleton");
		
		// find tables to populate
		logger.info("start analysing tables to populate");				
		populationAnalyzer.analysePopulation(project, update);
		logger.info("end analysing tables to populate");
		
		// generate script		
		logger.info("start script generation");
		List<String> scriptLines = generator.generateUpdateScript(update, dataSource, inputProvider, project);
		UpdateScriptFileWriterCommand writer = new UpdateScriptFileWriterCommand(project.model,databaseName,scriptLines);
		try {
			writer.execute();
		} catch (IOException ioe) {
			throw new UpdateFailureException("Error writing update script",ioe);
		}
		logger.info("end script generation");
								
		// print a update script summary
		logger.info("Update Report : ");
		updateReport.printUpdateReport(update);		
	}

}
