package org.sklsft.generator.skeletons.database;

import org.sklsft.generator.model.metadata.datasources.DataSourceMetaData;
import org.sklsft.generator.skeletons.layers.Layer;

public interface DatabaseHandler {

	String getName();
	
	String getDriverClassName();
	
	String getUrl(DataSourceMetaData datasource);
	
	String rename(String name);

	Layer getLayer();
	
}
