package com.skeleton.generator.bl.command.jdbc.impl;

import java.util.Map;

import javax.sql.DataSource;

import com.skeleton.generator.bl.command.jdbc.interfaces.InputSourceProvider;
import com.skeleton.generator.exception.DataSourceNotFoundException;

public class InputSourceProviderImpl implements InputSourceProvider {

	/*
	 * properties
	 */
	private Map<String, DataSource> inputSources;
	
	
	/*
	 * constructor
	 */
	public InputSourceProviderImpl(Map<String, DataSource> inputSources) {
		this.inputSources = inputSources;
	}

	
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		DataSource result = inputSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find input DataSource : " + dataSourceName);
		}
		
		return result;
	}
}
