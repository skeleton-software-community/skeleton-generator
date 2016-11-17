package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.FormBean;

public interface FormBeanFactory {

	FormBean getFormBean(Bean bean);
	
	FormBean getFormBean(OneToMany oneToMany);
}
