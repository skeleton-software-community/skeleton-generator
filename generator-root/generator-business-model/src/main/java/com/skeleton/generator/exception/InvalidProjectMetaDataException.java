package com.skeleton.generator.exception;

public class InvalidProjectMetaDataException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidProjectMetaDataException (String message) {
		super(message);
	}

	public InvalidProjectMetaDataException (String message, Throwable t) {
		super(message, t);
	}
}
