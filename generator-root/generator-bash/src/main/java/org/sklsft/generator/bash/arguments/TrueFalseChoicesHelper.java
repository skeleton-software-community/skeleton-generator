package org.sklsft.generator.bash.arguments;

import java.util.TreeMap;

/**
 * Simple implementation of an {@link AbstractMultiChoicesHelper} where you have to choose between true and false
 * @author Nicolas Thibault
 *
 */
public class TrueFalseChoicesHelper extends AbstractMultiChoicesHelper {

	public TrueFalseChoicesHelper(String question) {
		super();
		this.question = question;
	}

	private String question;
	
	@Override
	protected void initialize() {
		choices = new TreeMap<>();
		choices.put(String.valueOf(1), Boolean.TRUE.toString());
		choices.put(String.valueOf(2), Boolean.FALSE.toString());
	}

	@Override
	protected String getQuestion() {		
		return question;
	}	
}
