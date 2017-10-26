package org.sklsft.generator.exception;

public class PropertyNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PropertyNotFoundException (String message) {
		super(message);
	}

	public PropertyNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
