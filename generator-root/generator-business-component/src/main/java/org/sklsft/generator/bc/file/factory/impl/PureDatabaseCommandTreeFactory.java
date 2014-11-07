package org.sklsft.generator.bc.file.factory.impl;

import org.sklsft.generator.bc.file.strategy.impl.database.DefaultDatabaseStrategy;

public class PureDatabaseCommandTreeFactory extends AbstractFileWriteCommandTreeFactory {
	public PureDatabaseCommandTreeFactory() {
		super();

		//configurationStrategy = new SpringHibernateRichfacesConfigurationStrategy();

		layerStrategies.add(new DefaultDatabaseStrategy());
	}
}
