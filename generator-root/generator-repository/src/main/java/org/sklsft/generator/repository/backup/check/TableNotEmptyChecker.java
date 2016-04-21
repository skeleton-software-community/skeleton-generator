package org.sklsft.generator.repository.backup.check;

import javax.sql.DataSource;

import org.sklsft.generator.model.domain.database.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class TableNotEmptyChecker {

	private static final String COUNT_QUERY_SCRIPT = "SELECT COUNT(*) FROM ";
	private static final ResultSetExtractor<Boolean> extractor = new IsEmptyResultSetExtractor();
	
	
	public boolean isTableEmpty(DataSource dataSource, Table table){
		String script = COUNT_QUERY_SCRIPT + table.name;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(script, extractor);		
	}
}
