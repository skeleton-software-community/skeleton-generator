package org.sklsft.generator.bc.dump.reader;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.model.om.QualifiedColumn;
import org.sklsft.generator.model.om.Table;
import org.springframework.stereotype.Component;

/**
 * Generate a SQL script to read business table of a table.
 * 
 * @author Michael Fernandez
 */
@Component
public class ReadScriptGenerator {
	
	public String generateReadScript(Table table) {
		return createGet(table);
	}
	
	private String createGet(Table table) {
		StringBuffer script = new StringBuffer();
		
		script.append("SELECT ");
		
		List<String> fields = getSelectedFields(table.getQualifiedColumns());
		List<String> jointures = getJointures(table.getQualifiedColumns());
		
		boolean start = true;
		
		for (String field:fields) {
			if (start) {
				start = false;
			} else {
				script.append(",");
			}
			script.append(field);
		}
		
		script.append(" FROM " + table.name);
		
		for (String jointure:jointures) {
			script.append(" " + jointure);
		}
		
		return script.toString();
	}
	private List<String> getSelectedFields(List<QualifiedColumn> qualifiedColumns) {
		List<String> selectedFields = new ArrayList<String>();
		for (QualifiedColumn qualifiedColumn:qualifiedColumns) {
			if (qualifiedColumn.children != null) {
				selectedFields.addAll(getSelectedFields(qualifiedColumn.children));
			} else {
				selectedFields.add(qualifiedColumn.tableAlias + "." + qualifiedColumn.columnName);
			}
		}
		return selectedFields;
	}	

	private List<String> getJointures(List<QualifiedColumn> qualifiedColumns) {
		List<String> jointures = new ArrayList<String>();
		for (QualifiedColumn qualifiedColumn:qualifiedColumns) {
			if (qualifiedColumn.children != null) {
				jointures.add("LEFT OUTER JOIN " + qualifiedColumn.children.get(0).tableName + " " + qualifiedColumn.children.get(0).tableAlias + " ON " + qualifiedColumn.tableAlias + "." + qualifiedColumn.columnName + " = " + qualifiedColumn.children.get(0).tableAlias  + ".ID");
				jointures.addAll(getJointures(qualifiedColumn.children));
			}
		}
		return jointures;
	}

		
}
