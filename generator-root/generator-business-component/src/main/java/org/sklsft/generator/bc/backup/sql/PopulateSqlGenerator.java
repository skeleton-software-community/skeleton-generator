package org.sklsft.generator.bc.backup.sql;

import org.sklsft.generator.model.om.Table;

/**
 * Generate sql script to populate database
 * 
 * @author Michael Fernandez
 *
 */
public interface PopulateSqlGenerator {
	
	String generateInsertSQL(Table table);
}
