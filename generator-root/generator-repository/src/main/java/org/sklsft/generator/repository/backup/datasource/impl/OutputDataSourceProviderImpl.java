package org.sklsft.generator.repository.backup.datasource.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.sklsft.generator.exception.DataSourceNotFoundException;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;
import org.sklsft.generator.repository.backup.datasource.interfaces.OutputDataSourceProvider;

/**
 * Simple implementation of a {@link InputDataSourceProvider} with a Map of {@link DataSource}
 * @author Nicolas Thibault
 *
 */
public class OutputDataSourceProviderImpl implements OutputDataSourceProvider {

	/*
	 * properties
	 */
	private Map<String, DataSource> dataSources;
	
	
	/*
	 * constructor
	 */
	public OutputDataSourceProviderImpl(Map<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		DataSource result = dataSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find output DataSource : " + dataSourceName);
		}
		
		return result;
	}
}
