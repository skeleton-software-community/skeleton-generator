package com.skeleton.generator.exception;

public class BeanNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public BeanNotFoundException (String message) {
		super(message);
	}

	public BeanNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
