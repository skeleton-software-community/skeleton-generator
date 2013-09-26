package com.skeleton.generator.exception;

public class ProjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ProjectNotFoundException (String message) {
		super(message);
	}

	public ProjectNotFoundException (String message, Throwable t) {
		super(message, t);
	}
}
