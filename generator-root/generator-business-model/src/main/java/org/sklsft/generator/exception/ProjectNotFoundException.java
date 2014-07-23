package org.sklsft.generator.exception;

public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProjectNotFoundException (String message) {
		super(message);
	}

	public ProjectNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
