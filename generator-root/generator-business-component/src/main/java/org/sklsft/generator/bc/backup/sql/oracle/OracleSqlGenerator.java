package org.sklsft.generator.bc.backup.sql.oracle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sklsft.generator.bc.backup.sql.SqlGenerator;
import org.sklsft.generator.model.metadata.DataType;
import org.sklsft.generator.model.om.Column;
import org.sklsft.generator.model.om.Table;
import org.sklsft.generator.model.update.TableUpdate;

/**
 * Generate sql script to populate or create database structure for Oracle
 * Generate sql INSERT using stored function to retrieve id.
 * 
 * @author Michael Fernandez
 *
 */
public class OracleSqlGenerator implements SqlGenerator {

	private static final String NEW_LINE = "\n";
	
	@Override
	public List<String> generateConfigurationPopulation() {
		List<String> list = new ArrayList<String>();
		
		list.add("SET ESCAPE ON;");
		
		return list;
	}

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

	@Override
	public List<String> generateCreationTableSQL(Table table) {
		List<String> list = new ArrayList<String>();
		StringBuffer result = new StringBuffer();
		String 		 sequenceName = getSequenceName(table);
		
		result.append("-- create table --").append(NEW_LINE);
		result.append("CREATE TABLE " + table.name + NEW_LINE);
		result.append("(").append(NEW_LINE);
		result.append(table.columns.get(0).name + " " + DataType.getOracleType(table.columns.get(0).dataType));

		for (int i = 1; i < table.columns.size(); i++) {
			result.append(",");
			result.append(table.columns.get(i).name + " " + DataType.getOracleType(table.columns.get(i).dataType));
			if (table.columns.get(i).nullable) {
				result.append(" NULL");
			} else {
				result.append(" NOT NULL");
			}
			if (table.columns.get(i).unique) {
				if (!(i == 1 && table.cardinality == 1)) {
					result.append(" UNIQUE");
				}
			}
		}

		result.append(",");
		
		result.append("CONSTRAINT UC_" + table.name + " UNIQUE (" + table.columns.get(1).name);
		for (int i = 2; i <= table.cardinality; i++) {
			result.append("," + table.columns.get(i).name);
		}
		result.append(")");
		result.append("USING INDEX (CREATE INDEX UC_" + table.name + " ON " + table.name + "(" + table.columns.get(1).name);
		for (int i = 2; i <= table.cardinality; i++) {
			result.append("," + table.columns.get(i).name);
		}
		result.append(") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND)").append(NEW_LINE);
		
		result.append(", CONSTRAINT PK_" + table.name + " PRIMARY KEY (" + table.columns.get(0).name + ")").append(NEW_LINE);
		result.append("USING INDEX (CREATE INDEX PK_" + table.name + " ON " + table.name + "(" + table.columns.get(0).name + ") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND)").append(NEW_LINE);

		result.append(") TABLESPACE " + table.myPackage.model.project.databaseName + "_TBL").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());
		
		result = new StringBuffer();	
		result.append("-- create sequence --").append(NEW_LINE);
		result.append("CREATE SEQUENCE " + sequenceName + " MINVALUE 0 NOMAXVALUE START WITH 0 INCREMENT BY 1 NOCYCLE").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());

		if (table.myPackage.model.project.audited) {
			result = new StringBuffer();	
			result.append("-- create audit table --").append(NEW_LINE);
			result.append("CREATE TABLE " + table.name + "_AUD").append(NEW_LINE);
			result.append("(").append(NEW_LINE);
			result.append(table.columns.get(0).name + " int NOT NULL,").append(NEW_LINE);
			result.append("REV int NOT NULL,").append(NEW_LINE);
			result.append("REVTYPE smallint NOT NULL,").append(NEW_LINE);
	
	        for (int i = 1;i<table.columns.size();i++)
	        {
	        	result.append(table.columns.get(i).name + " " + DataType.getOracleType(table.columns.get(i).dataType) + " NULL,").append(NEW_LINE);
	        }
	
	        result.append("CONSTRAINT PK_" + table.name + "_AUD PRIMARY KEY (ID, REV),").append(NEW_LINE);
	        result.append("CONSTRAINT FK_" + table.name + "_AUD FOREIGN KEY (REV)").append(NEW_LINE);
	        result.append("REFERENCES AUDITENTITY (ID)").append(NEW_LINE);
	        result.append(") TABLESPACE " + table.myPackage.model.project.databaseName + "_AUD").append(NEW_LINE);
	        result.append("/").append(NEW_LINE);
	        list.add(result.toString());
	        
	        result = new StringBuffer();	
	        result.append("CREATE INDEX FK_" + table.name + "_AUD ON " + table.name + "_AUD(REV)").append(NEW_LINE);
	        result.append("/").append(NEW_LINE);
	        list.add(result.toString());
        
		}
		return list;
	}
	
	public String getSequenceName(Table table) {
		return table.name + "_id_seq";
	}
	

	@Override
	public List<String> generateUpdateTableSQL(TableUpdate tableUpdate) {
		List<String> list = new ArrayList<>();
		
		for (Column column : tableUpdate.getColumnRemoved()) {
			list.add("ALTER TABLE " + tableUpdate.getTable().name + " DROP " + column.name + ";" + NEW_LINE);
		}
		
		for (Column column : tableUpdate.getColumnAdded()) {
			list.add("ALTER TABLE " + tableUpdate.getTable().name + " ADD " + column.name + " " + DataType.getOracleType(column.dataType) + ";" + NEW_LINE);			
		}
		
		if (tableUpdate.getTable().myPackage.model.project.audited) {
			for (Column column : tableUpdate.getColumnRemoved()) {
				list.add("ALTER TABLE " + tableUpdate.getTable().name + "_AUD DROP " + column.name + ";" + NEW_LINE);
			}
			
			for (Column column : tableUpdate.getColumnAdded()) {
				list.add("ALTER TABLE " + tableUpdate.getTable().name + "_AUD ADD " + column.name + " " + DataType.getOracleType(column.dataType) + ";" + NEW_LINE);			
			}
		}

		return list;
	}

	@Override
	public List<String> generateDropProceduresTable(Table table) {
		List<String> list = new ArrayList<>();
		
		list.add(generateDropObjet("PROCEDURE", "get_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "find_" + table.name.toLowerCase()));
		list.add(generateDropObjet("FUNCTION", "find_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "ins_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "pins_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "ins_" + table.name.toLowerCase() + "_bc"));
		list.add(generateDropObjet("PROCEDURE", "upd_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "upd_" + table.name.toLowerCase() + "_bc"));
		list.add(generateDropObjet("PROCEDURE", "del_" + table.name.toLowerCase()));
		list.add(generateDropObjet("PROCEDURE", "del_" + table.name.toLowerCase() + "_bc"));

		return list;
	}

	@Override
	public List<String> generateProceduresTable(Table table) {
		List<String> list = new ArrayList<String>();
		List<Column> findColumnList = table.getFindColumnList();
		List<Column> tempColumnList;
		StringBuffer result = new StringBuffer();
		Map<String, String> fieldMap = createFieldMap(table);
		
			
		result.append("-- used to find element from business key --").append(NEW_LINE);
		result.append("CREATE OR REPLACE FUNCTION find_" + table.name.toLowerCase()).append(NEW_LINE);
		result.append("(").append(NEW_LINE);

		for (int i = 0; i < findColumnList.size(); i++) {
			if (i>0) {
				result.append(",");
			}
			result.append("v" + fieldMap.get(findColumnList.get(i).name) + " IN " + DataType.getPlOracleType(findColumnList.get(i).dataType)).append(NEW_LINE);
		}
		result.append(") RETURN NUMBER").append(NEW_LINE);

		result.append("AS").append(NEW_LINE);
		
		result.append("vID NUMBER;").append(NEW_LINE);
		
		for (int i = 1; i <= table.cardinality; i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v" + fieldMap.get(table.columns.get(i).name) + " NUMBER;").append(NEW_LINE);
			}
		}

		result.append("BEGIN").append(NEW_LINE);
		result.append("IF v" + fieldMap.get(findColumnList.get(0).name) + " IS NULL").append(NEW_LINE);

		for (int i = 1; i < findColumnList.size(); i++) {
			result.append("AND v" + fieldMap.get(findColumnList.get(i).name) + " IS NULL").append(NEW_LINE);
		}
		result.append("THEN").append(NEW_LINE);
		result.append("vID := NULL;").append(NEW_LINE);
		result.append("ELSE").append(NEW_LINE);

		for (int i = 1; i <= table.cardinality; i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v" + fieldMap.get(table.columns.get(i).name) + " := ");
				result.append("find_" + table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = table.columns.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					if (j > 0) {
						result.append(",");
					}
					result.append("v" + fieldMap.get(table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name));
				}

				result.append(");").append(NEW_LINE);
			}
		}

		result.append("SELECT " + table.name + ".ID").append(NEW_LINE);
		result.append("INTO vID").append(NEW_LINE);
		result.append("FROM " + table.name).append(NEW_LINE);
		result.append("WHERE " + table.name + "." + table.columns.get(1).name + " = v" + fieldMap.get(table.columns.get(1).name)).append(NEW_LINE);

		for (int i = 2; i <= table.cardinality; i++) {
			result.append("AND " + table.name + "." + table.columns.get(i).name + " = v" + fieldMap.get(table.columns.get(i).name)).append(NEW_LINE);
		}

		result.append(";").append(NEW_LINE);
		result.append("IF vID IS NULL").append(NEW_LINE);
		result.append("THEN").append(NEW_LINE);
		result.append("vID := -1;").append(NEW_LINE);
		result.append("END IF;").append(NEW_LINE);
		result.append("END IF;").append(NEW_LINE);
		result.append("RETURN vID;").append(NEW_LINE);
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		 list.add(result.toString());
	        
	        result = new StringBuffer();	

		result.append("-- used to insert a new element --").append(NEW_LINE);
		result.append("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase()).append(NEW_LINE);
		result.append("(").append(NEW_LINE);

		result.append("v" + table.columns.get(1).name + " IN " + DataType.getPlOracleType(table.columns.get(1).dataType));

		for (int i = 2; i < table.columns.size(); i++) {
			result.append(",").append(NEW_LINE);
			result.append("v" + table.columns.get(i).name + " IN " + DataType.getPlOracleType(table.columns.get(i).dataType));
		}	        	
		result.append(") AS").append(NEW_LINE);
		result.append("vREV NUMBER;").append(NEW_LINE);
		result.append("vID NUMBER;").append(NEW_LINE);
		result.append("BEGIN").append(NEW_LINE);
		result.append("vID := " + getSequenceName(table) + ".NEXTVAL;").append(NEW_LINE);
		result.append("INSERT INTO " + table.name + " (ID, " + table.columns.get(1).name);

		for (int i = 2; i < table.columns.size(); i++) {
			result.append(", " + table.columns.get(i).name);
		}

		result.append(") VALUES (vID, v" + table.columns.get(1).name);

		for (int i = 2; i < table.columns.size(); i++) {
			result.append(", v" + table.columns.get(i).name);
		}
		result.append(");").append(NEW_LINE);
		
		if (table.myPackage.model.project.audited) {
		
			result.append("vREV := hibernate_sequence.NEXTVAL;").append(NEW_LINE);
			
			result.append("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, current_millis(), 'sys');").append(NEW_LINE);
			
			result.append("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", " + table.columns.get(i).name);
			}
	
			result.append(") VALUES (vID, vREV, 0, v" + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", v" + table.columns.get(i).name);
			}
	
			result.append(");").append(NEW_LINE);
			
		}
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		 list.add(result.toString());
	        
	        result = new StringBuffer();	
		
		result.append("-- used in postinsert to update audit --").append(NEW_LINE);
		result.append("CREATE OR REPLACE PROCEDURE pins_" + table.name.toLowerCase()).append(NEW_LINE);
		result.append(" AS").append(NEW_LINE);
		if (table.myPackage.model.project.audited) {
			result.append("vREV NUMBER;").append(NEW_LINE);
			result.append("CURSOR cs"+table.name.toUpperCase()+" IS").append(NEW_LINE);
			result.append(" SELECT *").append(NEW_LINE);
			result.append(" FROM "+ table.name).append(NEW_LINE);
			result.append(" WHERE NOT EXISTS ").append(NEW_LINE); 
			result.append("  (SELECT 1").append(NEW_LINE);
			result.append("   FROM " + table.name + "_AUD").append(NEW_LINE);  
			result.append("   WHERE "+ table.name + ".ID = " +table.name + "_AUD.ID);").append(NEW_LINE);
			result.append("BEGIN").append(NEW_LINE);
				
			result.append("FOR el in cs" + table.name.toUpperCase()).append(NEW_LINE);
			result.append("LOOP").append(NEW_LINE);
			result.append("  vREV := hibernate_sequence.NEXTVAL;").append(NEW_LINE);
			
			result.append("  INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, current_millis(), 'sys');").append(NEW_LINE);
			
			result.append("  INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", " + table.columns.get(i).name);
			}
	
			result.append(") VALUES (el.ID, vREV, 0, el." + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", el." + table.columns.get(i).name);
			}
	
			result.append(");").append(NEW_LINE);
			result.append("END LOOP;").append(NEW_LINE);
		} else {			
			result.append("vREV NUMBER;").append(NEW_LINE);
			result.append("BEGIN").append(NEW_LINE);
			result.append("  vREV := 0;").append(NEW_LINE);
		}
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());
	        
	    result = new StringBuffer();	

		
		result.append("-- used to update an element --").append(NEW_LINE);
		result.append("CREATE OR REPLACE PROCEDURE upd_" + table.name.toLowerCase()).append(NEW_LINE);
		result.append("(").append(NEW_LINE);

		result.append("v" + table.columns.get(0).name + " IN " + DataType.getPlOracleType(table.columns.get(0).dataType));

		for (int i = 1; i < table.columns.size(); i++) {
			result.append(",").append(NEW_LINE);
			result.append("v" + table.columns.get(i).name + " IN " + DataType.getPlOracleType(table.columns.get(i).dataType));
		}

		result.append(") AS").append(NEW_LINE);
		result.append("vREV NUMBER;").append(NEW_LINE);
		result.append("BEGIN").append(NEW_LINE);
		result.append("UPDATE " + table.name + " set " + table.columns.get(1).name + " = v" + table.columns.get(1).name).append(NEW_LINE);

		for (int i = 2; i < table.columns.size(); i++) {
			result.append(", " + table.columns.get(i).name + " = v" + table.columns.get(i).name).append(NEW_LINE);
		}

		result.append("where ID = vID;").append(NEW_LINE);
		
		if (table.myPackage.model.project.audited) {
			
			result.append("vREV := hibernate_sequence.NEXTVAL;").append(NEW_LINE);
			
			result.append("INSERT INTO AUDITENTITY (ID, TIMESTAMP, LOGIN) VALUES (vREV, current_millis(), 'sys');").append(NEW_LINE);
			
			result.append("INSERT INTO " + table.name + "_AUD (ID, REV, REVTYPE, " + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", " + table.columns.get(i).name);
			}
	
			result.append(") VALUES (vID, vREV, 1, v" + table.columns.get(1).name);
	
			for (int i = 2; i < table.columns.size(); i++) {
				result.append(", v" + table.columns.get(i).name);
			}
	
			result.append(");").append(NEW_LINE);
		}
		
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());
	        
		return list;
	}
	

	@Override
	public List<String> generateProceduresByCodeTable(Table table) {
		List<String> list = new ArrayList<String>();
		StringBuffer result = new StringBuffer();
		Map<String, String> fieldMap = createFieldMap(table);
		
		List<Column> insertColumnList = table.getInsertColumnList();
		List<Column> tempColumnList;

		result.append("-- used to insert an element knowing business keys --").append(NEW_LINE);
		result.append("CREATE OR REPLACE PROCEDURE ins_" + table.name.toLowerCase() + "_bc").append(NEW_LINE);
		result.append("(").append(NEW_LINE);
		result.append("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			result.append(",").append(NEW_LINE);
			result.append("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		result.append(") AS").append(NEW_LINE);

		for (int i = 1; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v" + fieldMap.get(table.columns.get(i).name) + " NUMBER;").append(NEW_LINE);
			}
		}
		result.append("BEGIN").append(NEW_LINE);

		for (int i = 1; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v" + fieldMap.get(table.columns.get(i).name) + " := ");
				result.append("find_" + table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = table.columns.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					if (j > 0) {
						result.append(",");
					}
					result.append("v" + fieldMap.get(table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name));
				}

				result.append(");").append(NEW_LINE);
			}
		}

		result.append("ins_" + table.name.toLowerCase() + " (v" + fieldMap.get(table.columns.get(1).name));

		for (int i = 2; i < table.columns.size(); i++) {
			result.append(", v" + fieldMap.get(table.columns.get(i).name));
		}

		result.append(");").append(NEW_LINE);
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());
        
	    result = new StringBuffer();	

		result.append("-- used to update an element knowing business keys --").append(NEW_LINE);
		result.append("CREATE OR REPLACE PROCEDURE upd_" + table.name.toLowerCase() + "_bc").append(NEW_LINE);
		result.append("(").append(NEW_LINE);
		
		result.append("v" + fieldMap.get(insertColumnList.get(0).name) + " " + DataType.getPlOracleType(insertColumnList.get(0).dataType));

		for (int i = 1; i < insertColumnList.size(); i++) {
			result.append(",").append(NEW_LINE);
			result.append("v" + fieldMap.get(insertColumnList.get(i).name) + " " + DataType.getPlOracleType(insertColumnList.get(i).dataType));
		}

		result.append(") AS").append(NEW_LINE);

		result.append("vID NUMBER;").append(NEW_LINE);
		
		for (int i = 1; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v" + fieldMap.get(table.columns.get(i).name) + " NUMBER;").append(NEW_LINE);
			}
		}
		result.append("BEGIN").append(NEW_LINE);
		
		result.append("vID := find_" + table.name.toLowerCase() + " (");

		tempColumnList = table.getFindColumnList();

		for (int j = 0; j < tempColumnList.size(); j++) {
			if (j > 0) {
				result.append(",");
			}
			result.append("v" + fieldMap.get(tempColumnList.get(j).name));
		}

		result.append(");").append(NEW_LINE);

		for (int i = 1; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("v"+ fieldMap.get(table.columns.get(i).name) + " := ");
				result.append("find_" + table.columns.get(i).referenceTable.name.toLowerCase() + " (");

				tempColumnList = table.columns.get(i).referenceTable.getFindColumnList();

				for (int j = 0; j < tempColumnList.size(); j++) {
					if (j > 0) {
						result.append(",");
					}
					result.append("v" + fieldMap.get(table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name));
				}

				result.append(");").append(NEW_LINE);
			}
		}

		result.append("upd_" + table.name.toLowerCase() + " (vID");

		for (int i = 1; i < table.columns.size(); i++) {
			result.append(", v" + fieldMap.get(table.columns.get(i).name));
		}

		result.append(");").append(NEW_LINE);
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);
		list.add(result.toString());
        
		return list;
	}

	@Override
	public List<String> generateDropTableSQL(Table table) {
		List<String> list = new ArrayList<String>();

		list.add(generateDropObjet("TABLE", table.name));
		list.add(generateDropObjet("SEQUENCE", getSequenceName(table)));
		list.add(generateDropObjet("TABLE", table.name + "_AUD"));

		return list;
	}

	@Override
	public String generateDeleteSQL(Table table) {
		return "DELETE FROM " + table.name + ";";
	}

	@Override
	public List<String> generateDropIndexFkTable(Table table) {
		List<String> list = new ArrayList<String>();
		
		for (int i = 1; i < table.columns.size(); i++)  {
            if (table.columns.get(i).referenceTable != null) {
            	list.add(generateDropObjet("INDEX", "FK_" + table.name + "_" + i));
            }
        }
		return list;
	}

	
	@Override
	public List<String> generateAlterFKTableSQL(Table table) {
		List<String> list = new ArrayList<String>();
		StringBuffer result = new StringBuffer();

		result.append("-- table foreign keys and indexes --").append(NEW_LINE);
		for (int i = 1; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				result.append("ALTER TABLE " + table.name + " ADD CONSTRAINT FK_" + table.name + "_" + i + " FOREIGN KEY (" + table.columns.get(i).name + ") REFERENCES " + table.columns.get(i).referenceTable.name);
				if (table.columns.get(i).deleteCascade) {
					result.append(" ON DELETE CASCADE");
				}				
				result.append(NEW_LINE).append("/").append(NEW_LINE);
				list.add(result.toString());
		        
			    result = new StringBuffer();	

			}
		}
		
		for (int i = 1; i < table.columns.size(); i++)
        {
            if (table.columns.get(i).referenceTable != null)
            {
            	result.append("CREATE INDEX FK_" + table.name + "_" + i + " ON " + table.name + "(" + table.columns.get(i).name + ") TABLESPACE " + table.myPackage.model.project.databaseName + "_IND").append(NEW_LINE);
                result.append("/").append(NEW_LINE);
        		list.add(result.toString());
    	        
        	    result = new StringBuffer();	
            }
        }
	
		return list;
	}

	private Map<String, String> createFieldMap(Table table) {
		Map<String, String> fieldMap= new HashMap<>();
	

		for (int i = 0; i < table.getInsertColumnList().size(); i++) {
			fieldMap.put(table.getInsertColumnList().get(i).name, "ARG" + i);
		}
	
		for (int i = 0; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				fieldMap.put(table.columns.get(i).name, "ID_ARG" + i);
			}
		}
		return fieldMap;
	}
	
	private String generateDropObjet(String type, String name) {
		StringBuffer result = new StringBuffer();
		result.append("BEGIN").append(NEW_LINE);
		result.append("EXECUTE IMMEDIATE 'DROP "+ type + " " + name +"';").append(NEW_LINE);
		result.append("EXCEPTION").append(NEW_LINE);
		result.append("WHEN OTHERS THEN NULL;").append(NEW_LINE);
		result.append("END;").append(NEW_LINE);
		result.append("/").append(NEW_LINE);

		return result.toString();
	}

	@Override
	public String generateInsertByCode(Table table, Object[] values) {
		StringBuffer result = new StringBuffer();
		result.append("call ins_"+table.name.toLowerCase()+"_bc (");

		for (int i = 0; i < values.length; ++i) {
			Object value = values[i];
			if (i >0 ) {
				result.append(",");
			}
			result.append(convertToSqlString(value));
		}
		result.append(");");
		return result.toString();
	}

	@Override
	public String generateUpdateByCode(Table table, Object[] values) {
		StringBuffer result = new StringBuffer();
		result.append("call upd_"+table.name.toLowerCase()+"_bc (");

		for (int i = 0; i < values.length; ++i) {
			Object value = values[i];
			if (i >0 ) {
				result.append(",");
			}
			result.append(convertToSqlString(value));
		}
		result.append(");");
		return result.toString();
	}

	private String convertToSqlString(Object value) {
		if (value == null) {
			return "NULL";
		}

		if (value instanceof String) {
			return "'" + value.toString().replaceAll("'","''").replaceAll("\\&", "\\\\&") + "'"; 
		} else if (value instanceof Date) {			
			return "TO_DATE('" + new SimpleDateFormat("yyyy-MM-dd").format((Date)value)+ "', 'YYYY-MM-DD')";			
		} else if (value instanceof Boolean) {
			return ((Boolean)value) ? "1":"0";
		} else {
			return value.toString();
		}
	}

}
