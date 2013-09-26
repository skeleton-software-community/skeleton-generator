package com.skeleton.generator.bl.command.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.skeleton.generator.bl.command.jdbc.interfaces.BackupReader;
import com.skeleton.generator.bl.helper.query.QueryHelper;
import com.skeleton.generator.exception.ReadBackupFailureException;
import com.skeleton.generator.model.om.Table;

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
	        for (int i=1;i<=QueryHelper.getInsertArgsNumber(table);i++) {
	        	objectList.add(resultSet.getObject(i));
	        }
	        
	        return objectList.toArray();
	    }

	}

}
