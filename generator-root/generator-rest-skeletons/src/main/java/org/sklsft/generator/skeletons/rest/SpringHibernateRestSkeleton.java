package org.sklsft.generator.skeletons.rest;

import java.util.ArrayList;
import java.util.List;

import org.sklsft.generator.bc.resolvers.DatabaseHandlerDiscovery;
import org.sklsft.generator.model.domain.Project;
import org.sklsft.generator.skeletons.Skeleton;
import org.sklsft.generator.skeletons.core.layers.ApiLayer;
import org.sklsft.generator.skeletons.core.layers.BusinessComponentLayer;
import org.sklsft.generator.skeletons.core.layers.HibernateBusinessModelLayer;
import org.sklsft.generator.skeletons.core.layers.HibernateDaoLayer;
import org.sklsft.generator.skeletons.core.layers.JunitLayer;
import org.sklsft.generator.skeletons.core.layers.PopulatorLayer;
import org.sklsft.generator.skeletons.core.layers.ServiceLayer;
import org.sklsft.generator.skeletons.database.DatabaseHandler;
import org.sklsft.generator.skeletons.layers.Layer;
import org.sklsft.generator.skeletons.rest.layers.SpringRestControllerLayer;
import org.sklsft.generator.skeletons.rest.layers.SpringRestRootLayer;


public class SpringHibernateRestSkeleton implements Skeleton {

	@Override
	public String getName() {
		return "SPRING_HIBERNATE_REST";
	}
	
	@Override
	public List<Layer> getLayers(Project project) {
		List<Layer> layers = new ArrayList<>();
	
		for (DatabaseHandler handler:DatabaseHandlerDiscovery.handlers) {
			layers.add(handler.getLayer());
		}
		layers.add(new SpringRestRootLayer());
		layers.add(new ApiLayer());
		layers.add(new HibernateBusinessModelLayer());
		layers.add(new HibernateDaoLayer());
		layers.add(new BusinessComponentLayer());
		layers.add(new ServiceLayer());		
		layers.add(new SpringRestControllerLayer());
		layers.add(new PopulatorLayer());
		layers.add(new JunitLayer());
		
		return layers;
	}	
}
