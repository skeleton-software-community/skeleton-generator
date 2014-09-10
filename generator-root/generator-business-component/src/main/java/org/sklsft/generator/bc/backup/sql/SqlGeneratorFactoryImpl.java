package org.sklsft.generator.bc.backup.sql;

import org.sklsft.generator.bc.backup.sql.oracle.OracleSqlGenerator;
import org.sklsft.generator.bc.backup.sql.postgres.PostgresSqlGenerator;
import org.sklsft.generator.model.metadata.DatabaseEngine;
import org.springframework.stereotype.Component;

@Component
public class SqlGeneratorFactoryImpl implements SqlGeneratorFactory {

	@Override
	public SqlGenerator getSqlGenerator(DatabaseEngine engine) {
		if (DatabaseEngine.ORACLE.equals(engine)) {
			return new OracleSqlGenerator();
		} else {
			return new PostgresSqlGenerator();
		}
		
	}

}
