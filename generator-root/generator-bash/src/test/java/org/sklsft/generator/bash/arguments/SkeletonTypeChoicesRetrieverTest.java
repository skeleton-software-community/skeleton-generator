package org.sklsft.generator.bash.arguments;

import org.junit.Test;

public class SkeletonTypeChoicesRetrieverTest {

	@Test
	public void test() {
		System.out.println(new TrueFalseChoicesHelper("Please choose amongst").getFullMessage());
	}
}
