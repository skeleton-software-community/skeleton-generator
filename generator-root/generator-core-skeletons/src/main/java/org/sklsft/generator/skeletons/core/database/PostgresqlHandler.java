package org.sklsft.generator.skeletons.core.database;

import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;
import org.sklsft.generator.skeletons.core.layers.database.PostgresDatabaseLayer;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.sklsft.generator.skeletons.layers.Layer;

public class PostgresqlHandler implements DatabaseHandler {

public static final String NAME = "POSTGRESQL";
	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String getDriverClassName() {
		return "org.postgresql.Driver";
	}
	
	@Override
	public String getDialect() {
		return "org.hibernate.dialect.PostgreSQLDialect";
	}

	@Override
	public String getUrl(DataSourceMetaData datasource) {
		return "jdbc:postgresql://" + datasource.getHost() + ":" + datasource.getPort() + "/" + datasource.getDatabaseName();
	}

	@Override
	public String rename(String name) {
		return name;
	}
	
	@Override
	public Layer getLayer() {
		return new PostgresDatabaseLayer();
	}
}
