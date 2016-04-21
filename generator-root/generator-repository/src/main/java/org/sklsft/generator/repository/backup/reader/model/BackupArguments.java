package org.sklsft.generator.repository.backup.reader.model;

import java.util.List;

import org.sklsft.generator.model.backup.PopulateCommandType;

public class BackupArguments {

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
