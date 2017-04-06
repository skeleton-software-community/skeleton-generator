package org.sklsft.generator.skeletons.jsf;

import org.sklsft.generator.skeletons.EmptySkeleton;
import org.sklsft.generator.skeletons.core.layers.ApiLayer;
import org.sklsft.generator.skeletons.core.layers.BusinessComponentLayer;
import org.sklsft.generator.skeletons.core.layers.DefaultDatabaseLayer;
import org.sklsft.generator.skeletons.core.layers.HibernateBusinessModelLayer;
import org.sklsft.generator.skeletons.core.layers.HibernateDaoLayer;
import org.sklsft.generator.skeletons.core.layers.JunitLayer;
import org.sklsft.generator.skeletons.core.layers.PopulatorLayer;
import org.sklsft.generator.skeletons.core.layers.ServiceLayer;
import org.sklsft.generator.skeletons.jsf.layers.JsfControllerLayer;
import org.sklsft.generator.skeletons.jsf.layers.JsfModelLayer;
import org.sklsft.generator.skeletons.jsf.layers.Richfaces4PresentationLayer;


public class SpringHibernateRichFaces4Skeleton extends EmptySkeleton {

	public SpringHibernateRichFaces4Skeleton() {
		super();
		
		layers.add(new DefaultDatabaseLayer());
		layers.add(new ApiLayer());
		layers.add(new HibernateBusinessModelLayer());
		layers.add(new HibernateDaoLayer());
		layers.add(new BusinessComponentLayer());
		layers.add(new ServiceLayer());		
		layers.add(new JsfModelLayer());
		layers.add(new JsfControllerLayer());
		layers.add(new Richfaces4PresentationLayer());
		layers.add(new PopulatorLayer());
		layers.add(new JunitLayer());
	}
}
