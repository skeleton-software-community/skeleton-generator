package org.sklsft.generator.bc.backup.command.impl;

import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.bc.backup.command.interfaces.JdbcCommandFactory;
import org.sklsft.generator.bc.backup.sql.oracle.OracleSqlGenerator;
import org.sklsft.generator.model.metadata.DatabaseEngine;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.jdbc.impl.JdbcBatchInsertCommand;
import org.sklsft.generator.repository.backup.jdbc.impl.JdbcInsertCommand;
import org.sklsft.generator.repository.backup.jdbc.interfaces.JdbcCommand;

public class JdbcInsertCommandFactory implements JdbcCommandFactory{

	@Override
	public JdbcCommand getCommand(DataSource dataSource, Table table, List<Object[]> argsList) {
           if (table.myPackage.model.project.databaseEngine.equals(DatabaseEngine.ORACLE)) { 
        	   OracleSqlGenerator sqlGen = new OracleSqlGenerator();
               return new JdbcBatchInsertCommand(dataSource, table, argsList, sqlGen.generateInsertSQL(table));
           } else {
        	   return new JdbcInsertCommand(dataSource, table, argsList);
           }
	}
}
