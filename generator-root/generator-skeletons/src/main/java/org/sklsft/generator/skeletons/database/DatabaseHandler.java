package org.sklsft.generator.skeletons.database;

import org.sklsft.generator.skeletons.layers.Layer;

public interface DatabaseHandler {

	String getName();
	
	String rename(String name);

	Layer getLayer();
	
}
