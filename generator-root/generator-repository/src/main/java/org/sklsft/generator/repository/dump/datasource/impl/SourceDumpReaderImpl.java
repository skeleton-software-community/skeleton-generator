package org.sklsft.generator.repository.dump.datasource.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.exception.ReadDumpFailureException;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.dump.datasource.interfaces.SourceDumpReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Execute select SQL script and retrieve String result
 *  
 * @author Michael Fernandez
 *
 */
public class SourceDumpReaderImpl implements SourceDumpReader {

	/*
	 * properties
	 */
	private JdbcTemplate jdbcTemplate;
	private Table table;
	
	/*
	 * constructor
	 */
	public SourceDumpReaderImpl(DataSource dataSource, Table table) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.table = table;
	}
	
	
	public List<String[]> readDumpData(String script) throws ReadDumpFailureException {
		
		return jdbcTemplate.query(script, new BasicRowMapper());

	}
	


	private class BasicRowMapper implements RowMapper<String[]>{
		
		@Override
	    public String[] mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			String[] objects = new String[table.getInsertColumnList().size()];
	        for (int i=1;i<=table.getInsertColumnList().size();i++) {
	        	String result = resultSet.getString(i);
	        	objects[i-1] = (result == null) ? "" : result;
	        }
	        
	        return objects;
	    }

	}

}
