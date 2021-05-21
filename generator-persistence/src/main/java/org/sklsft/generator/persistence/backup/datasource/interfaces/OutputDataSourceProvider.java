package org.sklsft.generator.persistence.backup.datasource.interfaces;

import org.apache.commons.dbcp.BasicDataSource;
import org.sklsft.generator.model.exception.DataSourceNotFoundException;

/**
 * This class is used in your /data-model/datasource-context.xml<br/>
 * given a name, it gives a declared {@link BasicDataSource} that can be built then populated
 * @author Nicolas Thibault
 *
 */
public interface OutputDataSourceProvider {

	public BasicDataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;

}
