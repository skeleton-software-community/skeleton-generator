package org.sklsft.generator.bc.backup.sql;

import org.sklsft.generator.model.metadata.DatabaseEngine;

public interface SqlGeneratorFactory {
	public SqlGenerator getSqlGenerator(DatabaseEngine engine);
}
