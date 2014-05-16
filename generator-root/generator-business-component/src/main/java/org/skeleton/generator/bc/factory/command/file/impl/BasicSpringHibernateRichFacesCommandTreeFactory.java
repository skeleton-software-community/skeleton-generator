package org.skeleton.generator.bc.factory.command.file.impl;

import org.skeleton.generator.bc.strategy.impl.bc.BusinessComponentStrategy;
import org.skeleton.generator.bc.strategy.impl.configuration.SpringHibernateRichfacesConfigurationStrategy;
import org.skeleton.generator.bc.strategy.impl.controller.JsfControllerStrategy;
import org.skeleton.generator.bc.strategy.impl.dao.HibernateDaoStrategy;
import org.skeleton.generator.bc.strategy.impl.database.DefaultDatabaseStrategy;
import org.skeleton.generator.bc.strategy.impl.junit.JUnitStrategy;
import org.skeleton.generator.bc.strategy.impl.model.HibernateBusinessModelStrategy;
import org.skeleton.generator.bc.strategy.impl.presentation.BasicJsfPresentationStrategy;
import org.skeleton.generator.bc.strategy.impl.services.ServiceStrategy;


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
