package org.sklsft.generator.model.backup.check;

import org.sklsft.generator.model.domain.database.Table;

/**
 * A warning that can occur when checking a population plan before execution
 * @author Mounir Regragui
 *
 */
public class BackupPlanPreExecutionWarning {
	
	private BackupPlanWarningType type;
	private int step;
	private Table table;
	
	public BackupPlanPreExecutionWarning(BackupPlanWarningType type, int step, Table table) {
		super();
		this.type = type;
		this.step = step;
		this.table = table;
	}
	
	public BackupPlanWarningType getType() {
		return type;
	}
	public int getStep() {
		return step;
	}
	public Table getTable() {
		return table;
	}
	
	public static final int NO_STEP = -1;
}
