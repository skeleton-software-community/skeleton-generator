package org.sklsft.generator.bash.arguments;

/**
 * 
 * An interface that :
 * <li>Displays the choices we have regarding a question
 * <li>Returns the argument depending on the key we entered
 * 
 * @author Nicolas Thibault
 *
 */
public interface ChoicesHelper {	
	
	String getFullMessage();
	
	/**
	 * Every implementation should consider that invalid choices lead to null values
	 * that will be simply handled in a while condition
	 * @param key
	 * @return
	 */
	String getChoice(String key);
}
