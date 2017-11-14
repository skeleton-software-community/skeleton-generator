package org.sklsft.generator.skeletons.core.database;

import org.sklsft.generator.skeletons.core.layers.PostgresDatabaseLayer;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.sklsft.generator.skeletons.layers.Layer;

public class PostgresqlHandler implements DatabaseHandler {

	@Override
	public String getName() {
		return "POSTGRESQL";
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
