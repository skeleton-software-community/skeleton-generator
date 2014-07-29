package org.sklsft.generator.model.backup.check;

/**
 * The types of warnings for a script check
 * @author Mounir Regragui
 *
 */
public enum BackupPlanWarningType {

	/**
	 * Indicates that the script is not referencing the production db defined in a InputDataSourceProvider
	 */
	NOT_PROD_TARGET ("Input source is not production : "),
	
	/**
	 * Indicates that a table is still empty after population
	 */
	EMPTY_TABLE ("Empty Table : "),
	
	/**
	 * Indicates that hardcoded values will be injected
	 */
	HARDCODED_VALUES ("Hardcoded values will be injected : "),
	
	/**
	 * Indicates that there is no plan to populate a table
	 */
	NO_PLAN ("No population plan for table : ");
	
	
	private BackupPlanWarningType(String description) {
		this.description = description;
	}
	
	
	private String description;
	
	public String getDescription() {
		return description;
	}	
}
