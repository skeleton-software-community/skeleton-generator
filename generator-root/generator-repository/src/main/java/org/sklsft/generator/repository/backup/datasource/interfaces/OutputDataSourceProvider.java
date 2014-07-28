package org.sklsft.generator.repository.backup.datasource.interfaces;

import javax.sql.DataSource;

import org.sklsft.generator.exception.DataSourceNotFoundException;

/**
 * This class is used in your /data-model/CONTEXT/datasource-context.xml<br/>
 * given a name, it gives a declared {@link DataSource} that can be built then populated
 * @author Nicolas Thibault
 *
 */
public interface OutputDataSourceProvider {

	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;

}
