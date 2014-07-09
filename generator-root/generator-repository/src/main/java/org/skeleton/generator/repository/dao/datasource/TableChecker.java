package org.skeleton.generator.repository.dao.datasource;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.skeleton.generator.model.om.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TableChecker {

	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM DALLAS."; //TODO le dallas a mettre en parametre
	private static final ResultSetExtractor<Boolean> extractor = new IsEmptyResultSetExtractor();
	
	private JdbcTemplate jdbcTemplate;
	
	public TableChecker(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean isTableEmpty(Table table){
		String query = COUNT_QUERY + table.name;
		
		return jdbcTemplate.query(query, extractor);
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
