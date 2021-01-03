package org.sklsft.generator.util;

import org.junit.Assert;
import org.junit.Test;
import org.sklsft.generator.util.naming.JavaClassNaming;

public class JavaClassNamingTest {

	@Test
	public void testClassToDatabaseName() {
		String arg = "MyDummyClass";
		String expected = "MY_DUMMY_CLASS";
		String result = JavaClassNaming.toDatabaseName(arg);
		System.out.println(result);
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testObjectToDatabaseName() {
		String arg = "myDummyObject";
		String expected = "MY_DUMMY_OBJECT";
		String result = JavaClassNaming.toDatabaseName(arg);
		System.out.println(result);
		Assert.assertEquals(expected, result);
	}
}
