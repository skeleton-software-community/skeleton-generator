package org.sklsft.generator.exception;

public class BackupFileNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public BackupFileNotFoundException (String message) {
		super(message);
	}

	public BackupFileNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
