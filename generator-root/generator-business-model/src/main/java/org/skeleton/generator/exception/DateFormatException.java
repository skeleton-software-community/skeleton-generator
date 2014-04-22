package org.skeleton.generator.exception;

/**
 * This exception is thrown when reading a csv backup file and fail to convert a string to a date
 * @author Nicolas Thibault
 *
 */
public class DateFormatException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public DateFormatException (String message) {
		super(message);
	}

	public DateFormatException (String message, Throwable t) {
		super(message, t);
	}
}
