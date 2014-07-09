package org.skeleton.generator.model.check;

import org.skeleton.generator.model.om.Table;

/**
 * A warning that occurs after a script check
 * @author Mounir Regragui
 *
 */
public class ScriptCheckWarning {
	
	private WarningCheckType type;
	private int step;
	private Table table;
	
	public ScriptCheckWarning(WarningCheckType type, int step, Table table) {
		super();
		this.type = type;
		this.step = step;
		this.table = table;
	}
	
	public WarningCheckType getType() {
		return type;
	}
	public int getStep() {
		return step;
	}
	public Table getTable() {
		return table;
	}
}
