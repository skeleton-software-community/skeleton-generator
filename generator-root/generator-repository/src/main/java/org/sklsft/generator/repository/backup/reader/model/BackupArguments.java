package org.sklsft.generator.repository.backup.reader.model;

import java.util.List;

public class BackupArguments {

	private List<Object[]> arguments;
	private boolean argumentsTyped;
	
	
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
}
