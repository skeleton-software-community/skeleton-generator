package org.skeleton.generator.exception;

public class ProjectAlreadyConfiguredException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	public ProjectAlreadyConfiguredException (String message) {
		super(message);
	}

	public ProjectAlreadyConfiguredException (String message, Throwable t) {
		super(message, t);
	}
}
