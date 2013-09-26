package com.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.skeleton.generator.bl.command.jdbc.impl.JdbcInsertCommand;
import com.skeleton.generator.bl.command.jdbc.impl.TextDelimitedFileBackupReader;
import com.skeleton.generator.bl.command.jdbc.interfaces.BackupReader;
import com.skeleton.generator.bl.helper.naming.SQLNaming;
import com.skeleton.generator.bl.services.interfaces.TablePopulator;
import com.skeleton.generator.exception.PopulateTableFailureException;
import com.skeleton.generator.model.om.Table;

public class TextDelimitedTablePopulator implements TablePopulator {

	
	
	/*
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TextDelimitedTablePopulator.class);
	
	/*
	 * properties
	 */
	private Table table;
	private SimpleJdbcCall jdbcCall;
	private BackupReader backupReader;
	
	/*
	 * constructor
	 */
	public TextDelimitedTablePopulator(Table table, DataSource dataSource, String backupFilePath) {
		this.table = table;
		this.jdbcCall = new SimpleJdbcCall(dataSource);
		this.jdbcCall.setProcedureName(SQLNaming.getInsertProcedureName(table));
		this.backupReader = new TextDelimitedFileBackupReader(table, backupFilePath);
	}
	

	public void populateTable() throws PopulateTableFailureException {

		List<Object[]> argsList = backupReader.readBackupArgs();
		
		for (Object[] args:argsList) {
			JdbcInsertCommand command = new JdbcInsertCommand(jdbcCall, table, args);
			command.execute();
		}
	}
}
