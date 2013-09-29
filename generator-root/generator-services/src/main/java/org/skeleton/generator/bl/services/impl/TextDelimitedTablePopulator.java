package org.skeleton.generator.bl.services.impl;

import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.bc.command.jdbc.impl.JdbcInsertCommand;
import org.skeleton.generator.bl.services.interfaces.TablePopulator;
import org.skeleton.generator.exception.PopulateTableFailureException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.impl.TextDelimitedFileBackupReader;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupReader;
import org.skeleton.generator.util.naming.SQLNaming;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public class TextDelimitedTablePopulator implements TablePopulator {

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
		this.jdbcCall.setProcedureName(SQLNaming.getInsertProcedureName(table.name, table.myPackage.model.project.databaseEngine));
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
