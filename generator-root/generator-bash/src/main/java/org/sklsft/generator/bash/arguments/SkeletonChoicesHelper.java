package org.sklsft.generator.bash.arguments;

import java.util.TreeMap;

import org.sklsft.generator.bc.resolvers.SkeletonResolver;
import org.sklsft.generator.skeletons.Skeleton;

/**
 * The choices of the skeleton are available through a built map in the {@link SkeletonResolver}
 * @author Nicolas Thibault
 *
 */
public class SkeletonChoicesHelper extends AbstractMultiChoicesHelper {	
	
	@Override
	protected void initialize() {
		choices = new TreeMap<>();
		int i = 1;
		for (Skeleton skeleton:SkeletonResolver.skeletons.values()) {
			choices.put(String.valueOf(i), skeleton.getName());
			i++;
		}
	}

	@Override
	protected String getQuestion() {
		return "Enter your skeleton type";
	}	
}
