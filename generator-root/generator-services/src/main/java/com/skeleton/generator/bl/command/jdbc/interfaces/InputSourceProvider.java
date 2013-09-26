package com.skeleton.generator.bl.command.jdbc.interfaces;

import javax.sql.DataSource;

import com.skeleton.generator.exception.DataSourceNotFoundException;

public interface InputSourceProvider {

	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;
}
