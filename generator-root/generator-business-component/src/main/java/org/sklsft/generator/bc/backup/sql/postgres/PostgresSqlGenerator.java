package org.sklsft.generator.bc.backup.sql.postgres;

import java.util.List;

import org.sklsft.generator.bc.backup.sql.SqlGenerator;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.TableUpdate;

public class PostgresSqlGenerator implements SqlGenerator {

	@Override
	public List<String> generateConfigurationPopulation() {
		throw new RuntimeException("Not yet implemented for postgres");
	}
	
	@Override
	public String generateInsertSQL(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateCreationTableSQL(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateUpdateTableSQL(TableUpdate tableUpdate) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateDropProceduresTable(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateProceduresTable(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateProceduresByCodeTable(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateDropTableSQL(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public String generateDeleteSQL(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateDropIndexFkTable(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public List<String> generateAlterFKTableSQL(Table table) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public String generateInsertByCode(Table table, Object[] values) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

	@Override
	public String generateUpdateByCode(Table table, Object[] values) {
		throw new RuntimeException("Not yet implemented for postgres");
	}

}
