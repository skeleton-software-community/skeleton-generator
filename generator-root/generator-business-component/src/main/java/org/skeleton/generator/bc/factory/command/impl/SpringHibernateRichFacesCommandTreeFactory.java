package org.skeleton.generator.bc.factory.command.impl;

import org.skeleton.generator.bc.strategy.impl.presentation.JsfPresentationStrategy;


public class SpringHibernateRichFacesCommandTreeFactory extends AbstractFileWriteCommandTreeFactory {

	public SpringHibernateRichFacesCommandTreeFactory() {
		super();
		layerStrategies.add(new DefaultDatabaseStrategy());
		layerStrategies.add(new HibernateBusinessModelStrategy());
		layerStrategies.add(new HibernateDaoStrategy());
		layerStrategies.add(new BusinessComponentStrategy());
		layerStrategies.add(new ServiceStrategy());
		layerStrategies.add(new JUnitStrategy());
		layerStrategies.add(new JsfControllerStrategy());
		layerStrategies.add(new JsfPresentationStrategy());
	}
}
