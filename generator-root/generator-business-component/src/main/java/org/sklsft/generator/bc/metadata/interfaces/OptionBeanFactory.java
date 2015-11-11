package org.sklsft.generator.bc.metadata.interfaces;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.ui.OptionBean;

public interface OptionBeanFactory {

	OptionBean getOptionBean(Bean bean);
}
