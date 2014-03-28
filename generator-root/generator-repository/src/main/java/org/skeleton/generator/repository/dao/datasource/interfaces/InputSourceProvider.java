package org.skeleton.generator.repository.dao.datasource.interfaces;

import javax.sql.DataSource;

import org.skeleton.generator.exception.DataSourceNotFoundException;

/**
 * This class is used in your /data-model/CONTEXT/datasource-context.xml<br/>
 * given a name, it gives a declared {@link DataSource} that can be accessed to fecth data that will further be injected in your project datasource
 * @author Nicolas Thibault
 *
 */
public interface InputSourceProvider {

	public DataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;
}
