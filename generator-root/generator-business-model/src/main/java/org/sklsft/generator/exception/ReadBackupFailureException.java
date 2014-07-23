package org.sklsft.generator.exception;

public class ReadBackupFailureException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ReadBackupFailureException (String message) {
		super(message);
	}

	public ReadBackupFailureException (String message, Throwable t) {
		super(message, t);
	}
}
