package org.sklsft.generator.bc.backup.sql.oracle;

import org.sklsft.generator.bc.backup.sql.PopulateSqlGenerator;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Table;

/**
 * Generate sql script to populate database for Oracle
 * Generate sql INSERT using stored function to retrieve id.
 * 
 * @author Michael Fernandez
 *
 */
public class OracleSqlGenerator implements PopulateSqlGenerator {

	@Override
	public String generateInsertSQL(Table table) {
		boolean 		first;
		StringBuffer 	query = new StringBuffer();
		
		query.append("INSERT INTO ");
		query.append(table.name);
		
		query.append(" (");
		first = true;
		for (Column column : table.columns) {
			if (first) {
				first = false;
			} else {
				query.append(",");
			}
			query.append(column.name);
		}
		query.append(" ) VALUES (");		
		first = true;
		for (Column column : table.columns) {
			if (first) {
				first = false;
			} else {
				query.append(",");
			}
			if ("ID".equalsIgnoreCase(column.name)) {
				query.append(table.name).append("_id_seq.NEXTVAL");
			} else if (column.referenceTable == null) {
				query.append("?");
			} else {
				boolean 	firstParam;
				query.append("find_" + column.referenceTable.name.toLowerCase() + " (");
				firstParam = true;
				for (Column columnRef : column.referenceTable.getFindColumnList()) {
					if (firstParam) {
						firstParam = false;
					} else {
						query.append(",");
					}
					query.append("?");
				}
				query.append(")");					
			}
		}
		query.append(")");

		return query.toString();
	}

}
