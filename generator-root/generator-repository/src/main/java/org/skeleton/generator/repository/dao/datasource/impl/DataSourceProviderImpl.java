package org.skeleton.generator.repository.dao.datasource.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.skeleton.generator.exception.DataSourceNotFoundException;
import org.skeleton.generator.repository.dao.datasource.interfaces.DataSourceProvider;

/**
 * Simple implementation of a {@link DataSourceProvider} with a Map of {@link DataSource}
 * @author Nicolas Thibault
 *
 */
public class DataSourceProviderImpl implements DataSourceProvider {

	/*
	 * properties
	 */
	private Map<String, DataSource> dataSources;
	
	
	/*
	 * constructor
	 */
	public DataSourceProviderImpl(Map<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		DataSource result = dataSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find input DataSource : " + dataSourceName);
		}
		
		return result;
	}
}
