package org.sklsft.generator.repository.backup.datasource.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.sklsft.generator.exception.ReadBackupFailureException;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.repository.backup.datasource.interfaces.BackupArgumentReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation of a {@link BackupArgumentReader} that uses a script, a {@link JdbcTemplate} and a {@link Table} for meta-data
 * the {@link JdbcTemplate} is instantiated with a {@link DataSource} when instantiating the class
 * @author Nicolas Thibault
 *
 */
public class SourceAndScriptBackupReader{

	/*
	 * properties
	 */
	private JdbcTemplate jdbcTemplate;
	private Table table;
	
	/*
	 * constructor
	 */
	public SourceAndScriptBackupReader(DataSource dataSource, Table table) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.table = table;
	}
	
	
	public List<Object[]> readBackupArgs(String script) throws ReadBackupFailureException {
		
		return jdbcTemplate.query(script, new BasicRowMapper());

	}
	


	private class BasicRowMapper implements RowMapper<Object[]>{
		
		@Override
	    public Object[] mapRow(ResultSet resultSet, int rowNum) throws SQLException {

	        List<Object> objectList = new ArrayList<>();
	        for (int i=1;i<=table.getInsertColumnList().size();i++) {
	        	Object value = resultSet.getObject(i);
	        	// transform CLOB in string
	        	if (value instanceof Clob) {
	        		value = convertClobToString((Clob) value);
	        	}
	        	objectList.add(value);
	        }
	        
	        return objectList.toArray();
	    }

	}
	
	private static String convertClobToString(Clob clob) throws SQLException {
		try {
			Reader reader = clob.getCharacterStream();
			StringWriter stringWriter = new StringWriter();
			char[] buffer = new char[1024];
			int length;
			while ((length = reader.read(buffer, 0, buffer.length)) > 0) {
				stringWriter.write(buffer, 0, length);
			}
			
			return stringWriter.toString();
		} catch (IOException ioe) {
			throw new SQLException("error reading CLOB", ioe);
		}
	}

}
