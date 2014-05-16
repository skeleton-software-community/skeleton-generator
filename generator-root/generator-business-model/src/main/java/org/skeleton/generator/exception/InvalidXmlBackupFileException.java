package org.skeleton.generator.exception;

public class InvalidXmlBackupFileException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidXmlBackupFileException (String message) {
		super(message);
	}

	public InvalidXmlBackupFileException (String message, Throwable t) {
		super(message, t);
	}
}
