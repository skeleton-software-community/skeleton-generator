package org.sklsft.generator.model.backup.check;

import org.sklsft.generator.model.domain.database.Table;

/**
 * A warning that can occur after a population plan
 * @author Nicolas Thibault
 *
 */
public class BackupPlanPostExecutionWarning {
	
	private BackupPlanWarningType type;
	private Table table;
	
	public BackupPlanPostExecutionWarning(BackupPlanWarningType type, Table table) {
		super();
		this.type = type;
		this.table = table;
	}
	
	public BackupPlanWarningType getType() {
		return type;
	}
	public Table getTable() {
		return table;
	}
}
