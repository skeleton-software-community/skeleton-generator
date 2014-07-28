package org.sklsft.generator.exception;

public class InvalidProjectMetaDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidProjectMetaDataException (String message) {
		super(message);
	}

	public InvalidProjectMetaDataException (String message, Throwable t) {
		super(message, t);
	}
}
