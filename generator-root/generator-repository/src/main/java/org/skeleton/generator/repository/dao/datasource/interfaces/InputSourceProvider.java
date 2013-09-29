package org.skeleton.generator.repository.dao.datasource.interfaces;

import javax.sql.DataSource;

import org.skeleton.generator.exception.DataSourceNotFoundException;


public interface InputSourceProvider {

	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;
}
