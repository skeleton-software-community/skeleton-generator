package org.sklsft.generator.repository.backup.datasource.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.sklsft.generator.exception.DataSourceNotFoundException;
import org.sklsft.generator.repository.backup.datasource.interfaces.InputDataSourceProvider;

/**
 * Simple implementation of a {@link InputDataSourceProvider} with a Map of {@link DataSource}
 * @author Nicolas Thibault
 *
 */
public class InputDataSourceProviderImpl implements InputDataSourceProvider {

	/*
	 * properties
	 */
	private Map<String, DataSource> dataSources;
	private String productionSourceReference;
	
	
	/*
	 * constructor
	 */
	public InputDataSourceProviderImpl(Map<String, DataSource> dataSources, String productionSourceReference) {
		this.dataSources = dataSources;
		this.productionSourceReference = productionSourceReference;
	}

	
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

		DataSource result = dataSources.get(dataSourceName);
		
		if (result == null) {
			throw new DataSourceNotFoundException("Unable to find input DataSource : " + dataSourceName);
		}
		
		return result;
	}


	@Override
	public String getPoductionSourceReference() {
		return productionSourceReference;
	}
}
