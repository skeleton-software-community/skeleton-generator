package org.sklsft.generator.repository.dao.datasource.impl;

import java.util.List;

import org.sklsft.generator.model.backup.PopulateCommandType;

public class BackupCommandArguments {

	private List<Object[]> arguments;
	private PopulateCommandType type;
	
	
	public List<Object[]> getArguments() {
		return arguments;
	}
	public void setArguments(List<Object[]> arguments) {
		this.arguments = arguments;
	}
	public PopulateCommandType getType() {
		return type;
	}
	public void setType(PopulateCommandType type) {
		this.type = type;
	}
	
	
	
}
