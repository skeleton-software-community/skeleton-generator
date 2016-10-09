package org.sklsft.generator.bash.arguments;

import org.apache.commons.lang.StringUtils;


/**
 * An abstract implementation of {@link ChoicesHelper} based on a free entry with possible check and conversion
 * 
 * @author Nicolas Thibault
 *
 */
public class BasicFreeChoicesHelper implements ChoicesHelper {

	private String question;
	
		
	public BasicFreeChoicesHelper(String question) {
		super();
		this.question = question;
	}



	public String getQuestion() {
		return question;
	}



	@Override
	public String getFullMessage() {
		return question;
	}


	@Override
	public String getChoice(String key) {
		if (isValid(key)) {
			return convertKey(key);
		}
		return null;
	}
	
	
	/**
	 * can be overridden to convert input
	 * @param key
	 * @return
	 */
	protected String convertKey(String key) {
		return key;
	}

	
	/**
	 * can be overridden to validate input
	 * @param key
	 * @return
	 */
	protected boolean isValid(String key) {
		return !StringUtils.isEmpty(key);
	}
}
