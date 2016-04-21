package org.sklsft.generator.repository.backup.check;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class IsEmptyResultSetExtractor implements ResultSetExtractor<Boolean>{

	@Override
	public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
		rs.next();
		int count = rs.getInt(1);
		return count==0;
	}
}
