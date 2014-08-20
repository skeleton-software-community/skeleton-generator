package org.sklsft.generator.exception;

public class ReadDumpFailureException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ReadDumpFailureException (String message) {
		super(message);
	}

	public ReadDumpFailureException (String message, Throwable t) {
		super(message, t);
	}
}
