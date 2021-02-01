package org.sklsft.generator.repository.backup.datasource.impl;

import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.exception.DataSourceNotFoundException;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;

/**
 * Simple implementation of a {@link InputDataSourceProvider} with a Map of {@link BasicDataSource}
 * @author Nicolas Thibault
 *
 */
public class InputDataSourceProviderImpl implements InputDataSourceProvider {

	/*
	 * properties
	 */
	private Map<String, BasicDataSource> dataSources;
	
	
	/*
	 * constructor
	 */
	public InputDataSourceProviderImpl(Map<String, BasicDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	
	@Override
	public BasicDataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		BasicDataSource result = dataSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find input DataSource : " + dataSourceName);
		}
		
		return result;
	}
}
