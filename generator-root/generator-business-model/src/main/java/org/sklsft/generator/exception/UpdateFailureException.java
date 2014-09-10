package org.sklsft.generator.exception;

public class UpdateFailureException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public UpdateFailureException (String message) {
		super(message);
	}

	public UpdateFailureException (String message, Throwable t) {
		super(message, t);
	}
}
