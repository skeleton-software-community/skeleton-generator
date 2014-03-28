package org.skeleton.generator.repository.dao.datasource.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.skeleton.generator.exception.DataSourceNotFoundException;
import org.skeleton.generator.repository.dao.datasource.interfaces.InputSourceProvider;

/**
 * Simple implementation of a {@link InputSourceProvider} with a Map of {@link DataSource}
 * @author Nicolas Thibault
 *
 */
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
