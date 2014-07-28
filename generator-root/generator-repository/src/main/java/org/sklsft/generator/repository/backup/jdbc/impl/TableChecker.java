package org.sklsft.generator.repository.backup.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sklsft.generator.model.om.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TableChecker {

	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM ";
	private static final ResultSetExtractor<Boolean> extractor = new IsEmptyResultSetExtractor();
	
	private JdbcTemplate jdbcTemplate;
	
	public TableChecker(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean isTableEmpty(Table table){
		String query = buildQuery(table);
		
		return jdbcTemplate.query(query, extractor);
	}
	
	private String buildQuery(Table table){
		
		return COUNT_QUERY + table.name;

	}
	
	private static class IsEmptyResultSetExtractor implements ResultSetExtractor<Boolean>{

		@Override
		public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
			rs.next();
			int count = rs.getInt(1);
			return count==0;
		}
		
	}
	
}
