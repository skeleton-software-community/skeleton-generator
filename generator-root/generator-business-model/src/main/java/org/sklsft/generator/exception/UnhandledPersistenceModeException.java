package org.sklsft.generator.exception;

/**
 * Raised when an error occurs when parsing project configuration files
 * @author Mounir Regragui
 *
 */
public class UnhandledPersistenceModeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909954112331400976L;

	public UnhandledPersistenceModeException(String message) {
		super(message);
	}
	
	public UnhandledPersistenceModeException(String message, Throwable t) {
		super(message, t);
	}

}
