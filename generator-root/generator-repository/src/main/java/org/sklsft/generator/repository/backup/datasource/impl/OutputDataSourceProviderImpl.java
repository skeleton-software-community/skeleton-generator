package org.sklsft.generator.repository.backup.datasource.impl;

import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.exception.DataSourceNotFoundException;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;

/**
 * Simple implementation of a {@link InputDataSourceProvider} with a Map of {@link BasicDataSource}
 * @author Nicolas Thibault
 *
 */
public class OutputDataSourceProviderImpl implements OutputDataSourceProvider {

	/*
	 * properties
	 */
	private Map<String, BasicDataSource> dataSources;
	
	
	/*
	 * constructor
	 */
	public OutputDataSourceProviderImpl(Map<String, BasicDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	
	@Override
	public BasicDataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		BasicDataSource result = dataSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find output DataSource : " + dataSourceName);
		}
		
		return result;
	}
}
