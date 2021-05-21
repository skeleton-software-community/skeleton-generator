package org.sklsft.generator.components.metadata.factory.interfaces;

import java.util.List;

import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.business.OneToMany;
import org.sklsft.generator.model.domain.ui.ViewProperty;

public interface ViewPropertiesFactory {

	public List<ViewProperty> getReferenceProperties(Bean bean);
	
	public List<ViewProperty> getBasicViewProperties(Bean bean);
	
	public List<ViewProperty> getFormProperties(Bean bean);
	
	public List<ViewProperty> getBasicViewProperties(OneToMany oneToMany);
	
	public List<ViewProperty> getFormProperties(OneToMany oneToMany);
}
