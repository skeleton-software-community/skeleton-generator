package org.sklsft.generator.bc.metadata.factory.interfaces;

import org.sklsft.generator.model.domain.Model;
import org.sklsft.generator.model.domain.business.Bean;
import org.sklsft.generator.model.domain.database.Table;
import org.sklsft.generator.model.metadata.TableMetaData;

public interface BeanFactory {

	Bean scanBean(TableMetaData tableMetaData, Table table);
	
	Bean fillBean(TableMetaData tableMetaData, Table table, Model model);
	

}
