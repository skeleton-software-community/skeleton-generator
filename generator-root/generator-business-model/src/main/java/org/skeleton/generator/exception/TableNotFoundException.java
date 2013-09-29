package org.skeleton.generator.exception;

public class TableNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public TableNotFoundException (String message) {
		super(message);
	}

	public TableNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
