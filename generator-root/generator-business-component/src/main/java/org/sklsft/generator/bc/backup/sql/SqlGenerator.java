package org.sklsft.generator.bc.backup.sql;

import java.util.List;

import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.TableUpdate;

/**
 * Generate sql script to populate database
 * 
 * @author Michael Fernandez
 *
 */
public interface SqlGenerator {
	
	String generateInsertSQL(Table table);
	
	List<String> generateConfigurationPopulation();
	
	List<String> generateCreationTableSQL(Table table);
	
	List<String> generateAlterFKTableSQL(Table table);
	
	List<String> generateUpdateTableSQL(TableUpdate tableUpdate);
	
	List<String> generateDropProceduresTable(Table table);
	
	List<String> generateDropIndexFkTable(Table table);
	
	List<String> generateProceduresTable(Table table);
	
	List<String> generateProceduresByCodeTable(Table table);
	
	List<String> generateDropTableSQL(Table table);
	
	String generateDeleteSQL(Table table);
	
	String generateInsertByCode(Table table, Object[] values);
	
	String generateUpdateByCode(Table table, Object[] values);	
}
