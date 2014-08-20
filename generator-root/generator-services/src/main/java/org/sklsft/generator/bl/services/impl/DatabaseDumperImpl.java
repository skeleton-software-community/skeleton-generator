package org.sklsft.generator.bl.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.sklsft.generator.bc.dump.reader.DataTableReader;
import org.sklsft.generator.bc.dump.writer.DumpFileCommand;
import org.sklsft.generator.bl.services.interfaces.DatabaseDumper;
import org.sklsft.generator.exception.BackupFileNotFoundException;
import org.sklsft.generator.model.om.Package;
import org.sklsft.generator.model.om.Project;
import org.sklsft.generator.model.om.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dump business content of database into text files
 * @author Michael Fernandez
 */
@Component
public class DatabaseDumperImpl implements DatabaseDumper {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseDumperImpl.class);

	@Autowired 
	private DataTableReader dataTableReader;
	@Autowired
	private TablePopulator tablePopulator;
	@Autowired
	private DumpFileCommand writer;
	
	@Override
	public void dumpDatabase(DataSource dataSource, Project project, Set<String> tables) {

		logger.info("start dump database");

		for (Package myPackage:project.model.packages) {
			logger.info("start dump package : " + myPackage.name);

			for (Table table:myPackage.tables) {

				if (tables == null || tables.contains(table.originalName)) {

					logger.info("start dumping table : " + table.name);

					try {
						List<String[]> data = dataTableReader.readData(dataSource, table);
						writer.writeDataFile(table, data);
						logger.info("dump table : " + table.name + " completed ("+data.size()+" lines)");
					} catch (BackupFileNotFoundException e) {
						logger.error(e.getMessage());
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				} else {
					logger.info("table : " + table.name + " skipped");
				}
			}
			logger.info("dump package " + myPackage.name + " completed");
		}
		logger.info("dump database completed");

	}

}
