package org.sklsft.generator.repository.backup.file.model;

import java.util.List;

import org.sklsft.generator.model.backup.PopulateCommandType;

/**
 * representation of a csv file used to store backup data
 *
 */
public class CsvFile {
	
	/*
	 * properties
	 */
	private PopulateCommandType populateCommandType;
	private List<Object[]> data;
	
	
	/*
	 * getters and setters
	 */
	public PopulateCommandType getPopulateCommandType() {
		return populateCommandType;
	}
	public void setPopulateCommandType(PopulateCommandType populateCommandType) {
		this.populateCommandType = populateCommandType;
	}
	public List<Object[]> getData() {
		return data;
	}
	public void setData(List<Object[]> data) {
		this.data = data;
	}
}
