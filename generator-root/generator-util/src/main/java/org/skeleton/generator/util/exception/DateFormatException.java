package org.skeleton.generator.util.exception;

public class DateFormatException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public DateFormatException (String message) {
		super(message);
	}

	public DateFormatException (String message, Throwable t) {
		super(message, t);
	}
}
