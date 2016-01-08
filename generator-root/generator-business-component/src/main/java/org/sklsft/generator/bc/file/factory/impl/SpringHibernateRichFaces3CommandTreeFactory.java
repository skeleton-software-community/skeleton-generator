package org.sklsft.generator.bc.file.factory.impl;

import org.sklsft.generator.bc.file.strategy.impl.bc.BusinessComponentStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces3.Richfaces3ConfigurationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.configuration.richfaces3.Richfaces3ResourcesStrategy;
import org.sklsft.generator.bc.file.strategy.impl.controller.Richfaces3ControllerStrategy;
import org.sklsft.generator.bc.file.strategy.impl.dao.HibernateDaoStrategy;
import org.sklsft.generator.bc.file.strategy.impl.database.DefaultDatabaseStrategy;
import org.sklsft.generator.bc.file.strategy.impl.model.HibernateBusinessModelStrategy;
import org.sklsft.generator.bc.file.strategy.impl.population.PopulatorStrategy;
import org.sklsft.generator.bc.file.strategy.impl.presentation.JsfRichfaces3PresentationStrategy;
import org.sklsft.generator.bc.file.strategy.impl.services.ServiceStrategy;


public class SpringHibernateRichFaces3CommandTreeFactory extends AbstractFileWriteCommandTreeFactory {

	public SpringHibernateRichFaces3CommandTreeFactory() {
		super();
		
		fileImportStrategy = new Richfaces3ResourcesStrategy();
		
		configurationStrategy = new Richfaces3ConfigurationStrategy();
			
		
		layerStrategies.add(new DefaultDatabaseStrategy());
		layerStrategies.add(new HibernateBusinessModelStrategy());
		layerStrategies.add(new HibernateDaoStrategy());
		layerStrategies.add(new BusinessComponentStrategy());
		layerStrategies.add(new ServiceStrategy());
		layerStrategies.add(new PopulatorStrategy());
		layerStrategies.add(new Richfaces3ControllerStrategy());
		layerStrategies.add(new JsfRichfaces3PresentationStrategy());
	}
}
