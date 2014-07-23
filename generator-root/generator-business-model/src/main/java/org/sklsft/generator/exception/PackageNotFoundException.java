package org.sklsft.generator.exception;

public class PackageNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5929257156917427754L;

	public PackageNotFoundException(String packageName) {
		super("Could not find a package with name " + packageName);
	}
	
}
