package org.skeleton.generator.model.check;

/**
 * The types of warnings for a script check
 * @author Mounir Regragui
 *
 */
public enum WarningCheckType {

	/**
	 * Indicates that the script is not referencing the production db
	 */
	NOT_PROD_TARGET,
	
	/**
	 * Indicates that the script will read data from an empty table
	 */
	EMPTY_TABLE;
	
}
