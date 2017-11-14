package org.sklsft.generator.bash.arguments;

import java.util.TreeMap;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerResolver;
import org.sklsft.generator.skeletons.database.DatabaseHandler;

/**
 * The choices of the database engine are available through a built map in the {@link DatabaseHandlerResolver}
 * @author Nicolas Thibault
 *
 */
public class DatabaseEngineChoicesHelper extends AbstractMultiChoicesHelper {	
	
	@Override
	protected void initialize() {
		choices = new TreeMap<>();
		int i = 1;
		for (DatabaseHandler handler:DatabaseHandlerResolver.handlers.values()) {
			choices.put(String.valueOf(i), handler.getName());
			i++;
		}
	}

	@Override
	protected String getQuestion() {
		return "Enter your database engine";
	}	
}
