package org.sklsft.generator.bc.file.factory.impl;

import org.sklsft.generator.bc.file.strategy.impl.bc.BusinessComponentStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.SpringHibernateRichfacesConfigurationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.SpringHibernateRichfacesResourcesStrategy;
import org.sklsft.generator.bc.file.strategy.impl.controller.JsfControllerStrategy;
import org.sklsft.generator.bc.file.strategy.impl.dao.HibernateDaoStrategy;
import org.sklsft.generator.bc.file.strategy.impl.database.DefaultDatabaseStrategy;
import org.sklsft.generator.bc.file.strategy.impl.model.HibernateBusinessModelStrategy;
import org.sklsft.generator.bc.file.strategy.impl.population.PopulatorStrategy;
import org.sklsft.generator.bc.file.strategy.impl.presentation.JsfRichfaces3PresentationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.services.ServiceStrategy;


public class SpringHibernateRichFacesCommandTreeFactory extends AbstractFileWriteCommandTreeFactory {

	public SpringHibernateRichFacesCommandTreeFactory() {
		super();
		
		fileImportStrategy = new SpringHibernateRichfacesResourcesStrategy();
		
		configurationStrategy = new SpringHibernateRichfacesConfigurationStrategy();
			
		
		layerStrategies.add(new DefaultDatabaseStrategy());
		layerStrategies.add(new HibernateBusinessModelStrategy());
		layerStrategies.add(new HibernateDaoStrategy());
		layerStrategies.add(new BusinessComponentStrategy());
		layerStrategies.add(new ServiceStrategy());
		layerStrategies.add(new PopulatorStrategy());
		layerStrategies.add(new JsfControllerStrategy());
		layerStrategies.add(new JsfRichfaces3PresentationStrategy());
	}
}
