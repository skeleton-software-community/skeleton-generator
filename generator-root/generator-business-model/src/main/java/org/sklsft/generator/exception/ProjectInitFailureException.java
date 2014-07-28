package org.sklsft.generator.exception;

public class ProjectInitFailureException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ProjectInitFailureException (String message) {
		super(message);
	}

	public ProjectInitFailureException (String message, Throwable t) {
		super(message, t);
	}
}
