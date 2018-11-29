package org.sklsft.generator.repository.backup.datasource.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.exception.DataSourceNotFoundException;

/**
 * This class is used in your /data-model/CONTEXT/datasource-context.xml<br/>
 * given a name, it gives a declared {@link DataSource} that can be accessed to fecth data that will further be injected in your project datasource
 * @author Nicolas Thibault
 *
 */
public interface InputDataSourceProvider {

	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;

}
