package org.sklsft.generator.bash.arguments;

import java.util.TreeMap;

import org.sklsft.generator.model.metadata.DatabaseEngine;

/**
 * The choices of the database engine are available in an enumeration
 * @author Nicolas Thibault
 *
 */
public class DatabaseEngineChoicesHelper extends AbstractMultiChoicesHelper {	
	
	@Override
	protected void initialize() {
		choices = new TreeMap<>();
		int i = 1;
		for (DatabaseEngine databaseEngine:DatabaseEngine.values()) {
			choices.put(String.valueOf(i), databaseEngine.name());
			i++;
		}
	}

	@Override
	protected String getQuestion() {
		return "Enter your database engine";
	}	
}
