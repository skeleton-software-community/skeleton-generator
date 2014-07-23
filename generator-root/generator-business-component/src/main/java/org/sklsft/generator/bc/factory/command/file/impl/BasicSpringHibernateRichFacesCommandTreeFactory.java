package org.sklsft.generator.bc.factory.command.file.impl;

import org.sklsft.generator.bc.strategy.impl.bc.BusinessComponentStrategy;
import org.sklsft.generator.bc.strategy.impl.configuration.SpringHibernateRichfacesConfigurationStrategy;
import org.sklsft.generator.bc.strategy.impl.controller.JsfControllerStrategy;
import org.sklsft.generator.bc.strategy.impl.dao.HibernateDaoStrategy;
import org.sklsft.generator.bc.strategy.impl.database.DefaultDatabaseStrategy;
import org.sklsft.generator.bc.strategy.impl.junit.JUnitStrategy;
import org.sklsft.generator.bc.strategy.impl.model.HibernateBusinessModelStrategy;
import org.sklsft.generator.bc.strategy.impl.presentation.BasicJsfPresentationStrategy;
import org.sklsft.generator.bc.strategy.impl.services.ServiceStrategy;


public class BasicSpringHibernateRichFacesCommandTreeFactory extends AbstractFileWriteCommandTreeFactory {

	public BasicSpringHibernateRichFacesCommandTreeFactory() {
		super();
		
		configurationStrategy = new SpringHibernateRichfacesConfigurationStrategy();
		
		layerStrategies.add(new DefaultDatabaseStrategy());
		layerStrategies.add(new HibernateBusinessModelStrategy());
		layerStrategies.add(new HibernateDaoStrategy());
		layerStrategies.add(new BusinessComponentStrategy());
		layerStrategies.add(new ServiceStrategy());
		layerStrategies.add(new JUnitStrategy());
		layerStrategies.add(new JsfControllerStrategy());
		layerStrategies.add(new BasicJsfPresentationStrategy());
	}
}
