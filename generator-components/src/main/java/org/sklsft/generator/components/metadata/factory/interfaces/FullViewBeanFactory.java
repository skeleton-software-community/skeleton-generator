package org.sklsft.generator.components.metadata.factory.interfaces;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.FullViewBean;

public interface FullViewBeanFactory {

	FullViewBean getFullViewBean(Bean bean);
	
	FullViewBean getFullViewBean(OneToMany oneToMany);
}
