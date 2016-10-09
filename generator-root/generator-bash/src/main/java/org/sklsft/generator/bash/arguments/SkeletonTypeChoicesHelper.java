package org.sklsft.generator.bash.arguments;

import java.util.TreeMap;

import org.sklsft.generator.model.metadata.SkeletonType;

/**
 * The choices of the skeleton types are available in an enumeration
 * @author Nicolas Thibault
 *
 */
public class SkeletonTypeChoicesHelper extends AbstractMultiChoicesHelper {	
	
	@Override
	protected void initialize() {
		choices = new TreeMap<>();
		int i = 1;
		for (SkeletonType skeletonType:SkeletonType.values()) {
			choices.put(String.valueOf(i), skeletonType.name());
			i++;
		}
	}

	@Override
	protected String getQuestion() {
		return "Enter your skeleton type";
	}	
}
