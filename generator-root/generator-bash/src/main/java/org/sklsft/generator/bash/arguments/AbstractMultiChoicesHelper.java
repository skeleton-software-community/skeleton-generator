package org.sklsft.generator.bash.arguments;

import java.util.Map.Entry;
import java.util.SortedMap;

/**
 * An abstract implementation of {@link ChoicesHelper} based on a map of choices<br>
 * We have to override initialization to fetch the possible values<br>
 * Be careful that the access to choices is not thread-safe.<br>
 * We do not consider multi-threading
 * 
 * @author Nicolas Thibault
 *
 */
public abstract class AbstractMultiChoicesHelper implements ChoicesHelper {

	protected SortedMap<String, String> choices;
	private boolean initialized = false;

	protected abstract void initialize();
	
	public SortedMap<String, String> getChoices() {
		
		if (!initialized) {
			initialize();
			initialized = true;
		}
		
		return choices;
	}

	public String getChoice(String key) {
		return getChoices().get(key);
	}
	
	public String getChoicesDisplaying() {
		
		if (getChoices().entrySet().isEmpty()) {
			throw new IllegalStateException("This class must be used with several choices");
		}
		
		String result = " (";
		boolean start = true;
		
		for (Entry<String,String> entry : getChoices().entrySet()) {
			if (start) {
				start = false;
			} else {
				result += ", ";
			}
			result += entry.getKey() + ":" + entry.getValue();
		}
		
		result += ") :";
		
		return result;
	}
	
	protected abstract String getQuestion();
	
	public String getFullMessage() {
		String result = getQuestion();
		result += getChoicesDisplaying();
		return result;
	}
}
