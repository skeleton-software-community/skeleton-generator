package org.sklsft.generator.bc.file.factory.impl;

import org.sklsft.generator.bc.file.strategy.impl.api.ApiStrategy;
import org.sklsft.generator.bc.file.strategy.impl.bc.BusinessComponentStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces4.Richfaces4ConfigurationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces4.Richfaces4ResourcesStrategy;
import org.sklsft.generator.bc.file.strategy.impl.controller.Richfaces4ControllerStrategy;
import org.sklsft.generator.bc.file.strategy.impl.dao.HibernateDaoStrategy;
import org.sklsft.generator.bc.file.strategy.impl.database.DefaultDatabaseStrategy;
import org.sklsft.generator.bc.file.strategy.impl.model.HibernateBusinessModelStrategy;
import org.sklsft.generator.bc.file.strategy.impl.population.PopulatorStrategy;
import org.sklsft.generator.bc.file.strategy.impl.presentation.JsfRichfaces4PresentationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.services.ServiceStrategy;


public class SpringHibernateRichFaces4CommandTreeFactory extends AbstractFileWriteCommandTreeFactory {

	public SpringHibernateRichFaces4CommandTreeFactory() {
		super();
		
		fileImportStrategy = new Richfaces4ResourcesStrategy();
		
		configurationStrategy = new Richfaces4ConfigurationStrategy();
			
		
		layerStrategies.add(new DefaultDatabaseStrategy());
		layerStrategies.add(new ApiStrategy());
		layerStrategies.add(new HibernateBusinessModelStrategy());
		layerStrategies.add(new HibernateDaoStrategy());
		layerStrategies.add(new BusinessComponentStrategy());
		layerStrategies.add(new ServiceStrategy());
		layerStrategies.add(new PopulatorStrategy());
		layerStrategies.add(new Richfaces4ControllerStrategy());
		layerStrategies.add(new JsfRichfaces4PresentationStrategy());
	}
}
