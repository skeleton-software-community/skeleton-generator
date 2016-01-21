package org.sklsft.generator.repository.backup.file.model;

import java.util.List;

import org.sklsft.generator.model.backup.PopulateCommandType;
import org.sklsft.generator.model.metadata.DataType;

/**
 * representation of a csv file used to store backup data
 *
 */
public class CsvFile {
	
	/*
	 * properties
	 */
	private PopulateCommandType populateCommandType;
	private DataType[] types;
	private List<String[]> data;
	
	
	/*
	 * getters and setters
	 */
	public PopulateCommandType getPopulateCommandType() {
		return populateCommandType;
	}
	public void setPopulateCommandType(PopulateCommandType populateCommandType) {
		this.populateCommandType = populateCommandType;
	}
	public DataType[] getTypes() {
		return types;
	}
	public void setTypes(DataType[] types) {
		this.types = types;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
}
