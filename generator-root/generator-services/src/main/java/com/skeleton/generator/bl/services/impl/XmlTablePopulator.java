package com.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.skeleton.generator.bl.command.jdbc.impl.JdbcInsertCommand;
import com.skeleton.generator.bl.command.jdbc.impl.XmlFileBackupReader;
import com.skeleton.generator.bl.command.jdbc.interfaces.BackupReader;
import com.skeleton.generator.bl.command.jdbc.interfaces.InputSourceProvider;
import com.skeleton.generator.bl.helper.naming.SQLNaming;
import com.skeleton.generator.bl.services.interfaces.TablePopulator;
import com.skeleton.generator.exception.PopulateTableFailureException;
import com.skeleton.generator.model.om.Table;

public class XmlTablePopulator implements TablePopulator {

	private static final String BACKUP_FOLDER = "BACKUP";
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(XmlTablePopulator.class);
	
	/*
	 * properties
	 */
	private Table table;
	private SimpleJdbcCall jdbcCall;
	private BackupReader backupReader;
	
	/*
	 * constructor
	 */
	public XmlTablePopulator(Table table, DataSource dataSource, String backupFilePath, InputSourceProvider inputSourceProvider) {
		this.table = table;
		this.jdbcCall = new SimpleJdbcCall(dataSource);
		this.jdbcCall.setProcedureName(SQLNaming.getInsertProcedureName(table));
		this.backupReader = new XmlFileBackupReader(table, backupFilePath, inputSourceProvider);
	}
	

	public void populateTable() throws PopulateTableFailureException {
					
		List<Object[]> argsList = backupReader.readBackupArgs();
			
		for (Object[] args:argsList) {
			JdbcInsertCommand command = new JdbcInsertCommand(jdbcCall, table, args);
			command.execute();
		}
	}
}
