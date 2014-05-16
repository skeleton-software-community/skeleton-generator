package org.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bl.services.interfaces.TablePopulator;
import org.skeleton.generator.exception.PopulateTableFailureException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.XmlFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;
import org.skeleton.generator.repository.dao.jdbc.impl.JdbcInsertCommand;
import org.skeleton.generator.repository.dao.jdbc.interfaces.JdbcCommand;
import org.skeleton.generator.repository.jdbc.SQLNaming;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class XmlTablePopulator implements TablePopulator {
	
	/*
	 * properties
	 */
	private Table table;
	private DataSource dataSource;
	private BackupReader backupReader;
	
	/*
	 * constructor
	 */
	public XmlTablePopulator(Table table, DataSource dataSource, String backupFilePath, InputSourceProvider inputSourceProvider) {
		this.table = table;
		this.dataSource = dataSource;
		this.backupReader = new XmlFileBackupReader(table, backupFilePath, inputSourceProvider);
	}
	

	public void populateTable() throws PopulateTableFailureException {
					
		List<Object[]> argsList = backupReader.readBackupArgs();
			
		for (Object[] args:argsList) {
			JdbcCommand command = new JdbcInsertCommand(dataSource, table, args);
			command.execute();
		}
	}
}
