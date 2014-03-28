package org.skeleton.generator.repository.dao.datasource.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.skeleton.generator.exception.ReadBackupFailureException;
import org.skeleton.generator.model.om.Table;
import org.skeleton.generator.repository.dao.datasource.interfaces.BackupReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation of a {@link BackupReader} that uses a script, a {@link JdbcTemplate} and a {@link Table} for meta-data
 * the {@link JdbcTemplate} is instantiated with a {@link DataSource} when instantiating the class
 * @author Nicolas Thibault
 *
 */
public class SourceAndScriptBackupReader implements BackupReader {

	/*
	 * properties
	 */
	private String script;
	private JdbcTemplate jdbcTemplate;
	private Table table;
	
	/*
	 * constructor
	 */
	public SourceAndScriptBackupReader(Table table, String script, DataSource dataSource) {
		this.script = script;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.table = table;
	}
	
	
	@Override
	public List<Object[]> readBackupArgs() throws ReadBackupFailureException {

		return jdbcTemplate.query(script, new BasicRowMapper());

	}
	
	private class BasicRowMapper implements RowMapper<Object[]>{

	    @Override
	    public Object[] mapRow(ResultSet resultSet, int rowNum) throws SQLException {

	        List<Object> objectList = new ArrayList<>();
	        for (int i=1;i<=table.getInsertColumnList().size();i++) {
	        	objectList.add(resultSet.getObject(i));
	        }
	        
	        return objectList.toArray();
	    }

	}

}
