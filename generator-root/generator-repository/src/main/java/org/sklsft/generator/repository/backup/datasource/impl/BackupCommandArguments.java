package org.sklsft.generator.repository.backup.datasource.impl;

import java.util.List;

import org.sklsft.generator.model.backup.PopulateCommandType;

public class BackupCommandArguments {

	private List<Object[]> arguments;
	private boolean argumentsTyped;
	private PopulateCommandType type;
	
	
	public List<Object[]> getArguments() {
		return arguments;
	}
	public void setArguments(List<Object[]> arguments) {
		this.arguments = arguments;
	}
	public boolean isArgumentsTyped() {
		return argumentsTyped;
	}
	public void setArgumentsTyped(boolean argumentsTyped) {
		this.argumentsTyped = argumentsTyped;
	}
	public PopulateCommandType getType() {
		return type;
	}
	public void setType(PopulateCommandType type) {
		this.type = type;
	}	
}
