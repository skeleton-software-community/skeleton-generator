package org.skeleton.generator.exception;

/**
 * Raised when an error occurs when parsing project configuration files
 * @author Mounir Regragui
 *
 */
public class ConfigurationReadException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909954112331400976L;

	public ConfigurationReadException(Throwable t) {
		super(t);
	}
	
	public ConfigurationReadException(String msg){
		super(msg);
	}

}
